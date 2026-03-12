<script>
  // Importamos la función para crear inspecciones desde el store
  import { crearInspeccion, cargando } from '../stores/inspecciones.js';
  
  // Creamos un objeto vacío para guardar los datos del formulario
  let formulario = {
    maquina: '',      // Nombre o número de la máquina
    operador: '',     // Nombre del operador
    horometro: '',    // Número de horas de uso
    estado: '',       // Estado: Bueno, Regular, Malo
    observaciones: '' // Comentarios adicionales
  };
  
  // Objeto para guardar los errores de validación
  let errores = {};
  
  // Objeto para mostrar mensajes al usuario (éxito o error)
  let mensaje = { tipo: '', texto: '' };
  
  // Función para VALIDAR el formulario
  function validarFormulario() {
    errores = {}; // Limpiamos errores anteriores
    
    // Validar que máquina no esté vacía
    if (!formulario.maquina.trim()) {
      errores.maquina = 'El campo Máquina es obligatorio';
    }
    
    // Validar que operador no esté vacío
    if (!formulario.operador.trim()) {
      errores.operador = 'El campo Operador es obligatorio';
    }
    
    // Validar que horómetro sea un número positivo
    if (!formulario.horometro || isNaN(formulario.horometro) || Number(formulario.horometro) < 0) {
      errores.horometro = 'El Horómetro debe ser un número';
    }
    
    // Validar que haya seleccionado un estado
    if (!formulario.estado) {
      errores.estado = 'Seleccione el Estado de la máquina';
    }
    
    // Retorna true si no hay errores
    return Object.keys(errores).length === 0;
  }
  
  // Función para ENVIAR el formulario
  async function enviarFormulario() {
    // Primero validamos
    if (!validarFormulario()) return;
    
    // Preparamos los datos para enviar
    const datos = {
      maquina: formulario.maquina,
      operador: formulario.operador,
      horometro: Number(formulario.horometro), // Convertimos a número
      estado: formulario.estado,
      observaciones: formulario.observaciones
    };
    
    // Enviamos los datos usando la función del store
    const resultado = await crearInspeccion(datos);
    
    // Si fue exitoso, mostramos mensaje y limpiamos
    if (resultado.success) {
      mensaje = { tipo: 'exito', texto: 'Inspección registrada correctamente' };
      limpiarFormulario();
    } else {
      mensaje = { tipo: 'error', texto: resultado.message || 'Error al registrar' };
    }
    
    // El mensaje desaparece después de 3 segundos
    setTimeout(() => {
      mensaje = { tipo: '', texto: '' };
    }, 3000);
  }
  
  // Función para LIMPIAR el formulario
  function limpiarFormulario() {
    formulario = {
      maquina: '',
      operador: '',
      horometro: '',
      estado: '',
      observaciones: ''
    };
    errores = {};
  }
</script>

<!-- Esta es la parte HTML del componente -->
<div class="form-container">
  <!-- Encabezado del formulario -->
  <div class="form-header">
    <h2>Registrar Nueva Inspeccion</h2>
    <p>Complete todos los campos obligatorios</p>
  </div>
  
  <!-- Mensaje de éxito o error -->
  {#if mensaje.texto}
    <div class="mensaje {mensaje.tipo}">
      {mensaje.texto}
    </div>
  {/if}
  
  <!-- El formulario -->
  <form on:submit|preventDefault={enviarFormulario}>
    <div class="form-grid">
      <!-- Campo: Máquina -->
      <div class="form-group">
        <label for="maquina">Máquina *</label>
        <input 
          type="text" 
          id="maquina" 
          bind:value={formulario.maquina}
          placeholder="Ingrese el nombre de la máquina"
          class:error={errores.maquina}
        />
        <!-- Mostrar error si existe -->
        {#if errores.maquina}
          <span class="error-text">{errores.maquina}</span>
        {/if}
      </div>
      
      <!-- Campo: Operador -->
      <div class="form-group">
        <label for="operador">Operador *</label>
        <input 
          type="text" 
          id="operador" 
          bind:value={formulario.operador}
          placeholder="Ingrese el nombre del operador"
          class:error={errores.operador}
        />
        {#if errores.operador}
          <span class="error-text">{errores.operador}</span>
        {/if}
      </div>
      
      <!-- Campo: Horómetro -->
      <div class="form-group">
        <label for="horometro">Horómetro *</label>
        <input 
          type="number" 
          id="horometro" 
          bind:value={formulario.horometro}
          placeholder="Ingrese el valor del horómetro"
          step="0.1"
          min="0"
          class:error={errores.horometro}
        />
        {#if errores.horometro}
          <span class="error-text">{errores.horometro}</span>
        {/if}
      </div>
      
      <!-- Campo: Estado (lista desplegable) -->
      <div class="form-group">
        <label for="estado">Estado de la máquina *</label>
        <select 
          id="estado" 
          bind:value={formulario.estado}
          class:error={errores.estado}
        >
          <option value="">Seleccione...</option>
          <option value="Bueno">Bueno</option>
          <option value="Regular">Regular</option>
          <option value="Malo">Malo</option>
        </select>
        {#if errores.estado}
          <span class="error-text">{errores.estado}</span>
        {/if}
      </div>
      
      <!-- Campo: Observaciones (texto largo) -->
      <div class="form-group full-width">
        <label for="observaciones">Observaciones</label>
        <textarea 
          id="observaciones" 
          bind:value={formulario.observaciones}
          placeholder="Ingrese las observaciones..."
          rows="4"
        ></textarea>
      </div>
    </div>
    
    <!-- Botones del formulario -->
    <div class="form-actions">
      <button type="button" class="btn-secondary" on:click={limpiarFormulario}>
        Limpiar
      </button>
      <button type="submit" class="btn-primary" disabled={$cargando}>
        {$cargando ? 'Guardando...' : 'Registrar inspección'}
      </button>
    </div>
  </form>
</div>

<!-- Esta es la parte de ESTILOS (CSS) -->
<style>
  /* Contenedor principal del formulario */
  .form-container {
    background: white;
    border-radius: 1rem;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
    overflow: hidden;
  }

  /* Encabezado con título */
  .form-header {
    background: #f8fafc;
    padding: 1.5rem 2rem;
    border-bottom: 1px solid #e2e8f0;
  }

  .form-header h2 {
    font-size: 1.25rem;
    color: #1e293b;
    margin-bottom: 0.25rem;
  }

  .form-header p {
    color: #64748b;
    font-size: 0.875rem;
  }

  /* El formulario */
  form {
    padding: 2rem;
  }

  /* Grid para poner campos en dos columnas */
  .form-grid {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 1.5rem;
  }

  /* Cada grupo de campo */
  .form-group {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
  }

  /* Campo de observaciones ocupa todo el ancho */
  .form-group.full-width {
    grid-column: span 2;
  }

  /* Etiquetas de los campos */
  label {
    font-weight: 500;
    color: #374151;
    font-size: 0.875rem;
  }

  /* Inputs, selects y textareas */
  input, select, textarea {
    padding: 0.75rem 1rem;
    border: 1px solid #d1d5db;
    border-radius: 0.5rem;
    font-size: 0.9375rem;
    transition: border-color 0.2s, box-shadow 0.2s;
  }

  /* Cuando el campo tiene foco */
  input:focus, select:focus, textarea:focus {
    outline: none;
    border-color: #3b82f6;
    box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
  }

  /* Estilo cuando hay error */
  input.error, select.error {
    border-color: #ef4444;
  }

  /* Texto del error */
  .error-text {
    color: #ef4444;
    font-size: 0.75rem;
  }

  /* Botones */
  .form-actions {
    display: flex;
    justify-content: flex-end;
    gap: 1rem;
    margin-top: 2rem;
    padding-top: 1.5rem;
    border-top: 1px solid #e2e8f0;
  }

  .btn-primary, .btn-secondary {
    padding: 0.75rem 1.5rem;
    border-radius: 0.5rem;
    font-weight: 500;
    cursor: pointer;
    transition: all 0.2s;
  }

  /* Botón principal (azul) */
  .btn-primary {
    background: #1e40af;
    color: white;
    border: none;
  }

  .btn-primary:hover:not(:disabled) {
    background: #1e3a8a;
  }

  .btn-primary:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }

  /* Botón secundario (gris) */
  .btn-secondary {
    background: white;
    color: #64748b;
    border: 1px solid #d1d5db;
  }

  .btn-secondary:hover {
    background: #f8fafc;
  }

  /* Mensajes de éxito/error */
  .mensaje {
    padding: 1rem;
    border-radius: 0.5rem;
    margin: 1rem 2rem 0;
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

  /* Para pantallas pequeñas: una columna */
  @media (max-width: 768px) {
    .form-grid {
      grid-template-columns: 1fr;
    }

    .form-group.full-width {
      grid-column: span 1;
    }
  }
</style>
