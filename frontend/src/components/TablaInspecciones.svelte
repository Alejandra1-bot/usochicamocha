<script>
  import { inspecciones, cargando, eliminarInspeccion, cargarInspecciones } from '../stores/inspecciones.js';
  import { onMount } from 'svelte';
  
  let mensaje = { tipo: '', texto: '' };
  let idAEliminar = null;
  
  // Cargar datos al montar el componente
  onMount(() => {
    cargarInspecciones();
  });
  
  function mostrarConfirmacion(id) {
    idAEliminar = id;
    mensaje = { tipo: 'advertencia', texto: '¿Está seguro de eliminar esta inspección?' };
  }
  
  async function confirmarEliminar() {
    if (idAEliminar) {
      const resultado = await eliminarInspeccion(idAEliminar);
      
      if (resultado.success) {
        mensaje = { tipo: 'exito', texto: 'Inspección eliminada correctamente' };
      } else {
        mensaje = { tipo: 'error', texto: resultado.message };
      }
      
      idAEliminar = null;
      setTimeout(() => {
        mensaje = { tipo: '', texto: '' };
      }, 3000);
    }
  }
  
  function cancelarEliminar() {
    idAEliminar = null;
    mensaje = { tipo: '', texto: '' };
  }
  
  function getBadgeClass(estado) {
    switch (estado) {
      case 'Bueno': return 'badge-bueno';
      case 'Regular': return 'badge-regular';
      case 'Malo': return 'badge-malo';
      default: return 'badge-default';
    }
  }
</script>

<div class="tabla-container">
  <div class="tabla-header">
    <h2>Inspecciones Registradas</h2>
    <button class="btn-refresh" on:click={cargarInspecciones} disabled={$cargando}>
      <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M21.5 2v6h-6M2.5 22v-6h6M2 11.5a10 10 0 0 1 18.8-4.3M22 12.5a10 10 0 0 1-18.8 4.2"/>
      </svg>
      Actualizar
    </button>
  </div>
  
  {#if mensaje.texto}
    <div class="mensaje {mensaje.tipo}">
      {mensaje.texto}
      {#if mensaje.tipo === 'advertencia'}
        <div class="mensaje-botones">
          <button class="btn-confirmar" on:click={confirmarEliminar}>Sí, eliminar</button>
          <button class="btn-cancelar" on:click={cancelarEliminar}>Cancelar</button>
        </div>
      {/if}
    </div>
  {/if}
  
  {#if $cargando}
    <div class="loading">Cargando inspecciones...</div>
  {:else if $inspecciones.length === 0}
    <div class="empty-state">
      <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
        <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
        <polyline points="14 2 14 8 20 8"></polyline>
        <line x1="12" y1="18" x2="12" y2="12"></line>
        <line x1="9" y1="15" x2="15" y2="15"></line>
      </svg>
      <p>No hay inspecciones registradas</p>
      <p class="sub-text">Registre una inspección usando el formulario</p>
    </div>
  {:else}
    <div class="tabla-wrapper">
      <table>
        <thead>
          <tr>
            <th>#</th>
            <th>Máquina</th>
            <th>Operador</th>
            <th>Horómetro</th>
            <th>Estado</th>
            <th>Observaciones</th>
            <th>Última</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {#each $inspecciones as inspeccion, index (inspeccion.id)}
            <tr>
              <td class="row-number">{index + 1}</td>
              <td>{inspeccion.numeroMaquina || inspeccion.maquina || '-'}</td>
              <td>{inspeccion.nombreOperador || inspeccion.operador || '-'}</td>
              <td class="numero">{inspeccion.horometro || '-'}</td>
              <td>
                <span class="badge {getBadgeClass(inspeccion.estado)}">
                  {inspeccion.estado || 'No definido'}
                </span>
              </td>
              <td class="observaciones">{inspeccion.observaciones || '-'}</td>
              <td>{inspeccion.ultimaActualizacion || '-'}</td>
              <td>
                <button class="btn-delete" on:click={() => mostrarConfirmacion(inspeccion.id)} title="Eliminar">
                  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <polyline points="3 6 5 6 21 6"></polyline>
                    <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                  </svg>
                </button>
              </td>
            </tr>
          {/each}
        </tbody>
      </table>
    </div>
  {/if}
</div>

<style>
  .tabla-container {
    background: white;
    border-radius: 1rem;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
    overflow: hidden;
  }

  .tabla-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1.5rem 2rem;
    background: #f8fafc;
    border-bottom: 1px solid #e2e8f0;
  }

  .tabla-header h2 {
    font-size: 1.25rem;
    color: #1e293b;
  }

  .btn-refresh {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.5rem 1rem;
    background: white;
    border: 1px solid #d1d5db;
    border-radius: 0.5rem;
    color: #374151;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.2s;
  }

  .btn-refresh:hover:not(:disabled) {
    background: #f8fafc;
  }

  .tabla-wrapper {
    overflow-x: auto;
  }

  table {
    width: 100%;
    border-collapse: collapse;
    font-family: Arial, sans-serif;
  }

  th, td {
    padding: 0.75rem 1rem;
    text-align: left;
    border-bottom: 1px solid #e2e8f0;
  }

  th {
    background: #f1f5f9;
    font-weight: 600;
    color: #475569;
    font-size: 0.8rem;
    text-transform: uppercase;
    letter-spacing: 0.05em;
    border-bottom: 2px solid #cbd5e1;
  }

  td {
    color: #334155;
    font-size: 0.875rem;
  }

  tr:hover {
    background: #f8fafc;
  }

  tr:nth-child(even) {
    background: #fafafa;
  }

  tr:nth-child(even):hover {
    background: #f1f5f9;
  }

  .row-number {
    color: #94a3b8;
    font-weight: 500;
    text-align: center;
    width: 50px;
  }

  .numero {
    text-align: right;
    font-family: 'Courier New', monospace;
    font-weight: 500;
  }

  .observaciones {
    max-width: 250px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .badge {
    display: inline-block;
    padding: 0.25rem 0.75rem;
    border-radius: 9999px;
    font-size: 0.75rem;
    font-weight: 500;
  }

  .badge-bueno {
    background: #dcfce7;
    color: #166534;
  }

  .badge-regular {
    background: #fef9c3;
    color: #854d0e;
  }

  .badge-malo {
    background: #fee2e2;
    color: #991b1b;
  }

  .badge-default {
    background: #f1f5f9;
    color: #64748b;
  }

  .btn-delete {
    padding: 0.5rem;
    background: #fee2e2;
    border: none;
    border-radius: 0.375rem;
    color: #dc2626;
    cursor: pointer;
    transition: all 0.2s;
  }

  .btn-delete:hover {
    background: #fecaca;
  }

  .loading, .empty-state {
    text-align: center;
    padding: 3rem;
    color: #64748b;
  }

  .empty-state svg {
    margin-bottom: 1rem;
    opacity: 0.5;
  }

  .empty-state .sub-text {
    font-size: 0.875rem;
    margin-top: 0.5rem;
    color: #94a3b8;
  }

  .mensaje {
    padding: 1rem;
    margin: 1rem;
    border-radius: 0.5rem;
    font-weight: 500;
  }

  .mensaje.exito {
    background: #dcfce7;
    color: #166534;
  }

  .mensaje.error {
    background: #fee2e2;
    color: #991b1b;
  }
  
  .mensaje.advertencia {
    background: #fef9c3;
    color: #854d0e;
    display: flex;
    flex-direction: column;
    gap: 0.75rem;
  }
  
  .mensaje-botones {
    display: flex;
    gap: 0.5rem;
    justify-content: center;
    margin-top: 0.5rem;
  }
  
  .btn-confirmar {
    background: #dc2626;
    color: white;
    border: none;
    padding: 0.5rem 1rem;
    border-radius: 0.375rem;
    cursor: pointer;
    font-weight: 500;
  }
  
  .btn-confirmar:hover {
    background: #b91c1c;
  }
  
  .btn-cancelar {
    background: #f1f5f9;
    color: #475569;
    border: 1px solid #cbd5e1;
    padding: 0.5rem 1rem;
    border-radius: 0.375rem;
    cursor: pointer;
    font-weight: 500;
  }
  
  .btn-cancelar:hover {
    background: #e2e8f0;
  }
</style>
