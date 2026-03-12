<script>
  import { createEventDispatcher } from 'svelte';
  
  export let vistaActual;
  
  const dispatch = createEventDispatcher();
  
  function cambiarVista(vista) {
    dispatch('cambiarVista', vista);
  }
</script>

<nav>
  <div class="nav-content">
    <button 
      class="nav-btn"
      class:active={vistaActual === 'registro'}
      on:click={() => cambiarVista('registro')}
    >
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
        <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
      </svg>
      Registro
    </button>
    <button 
      class="nav-btn"
      class:active={vistaActual === 'tabla'}
      on:click={() => cambiarVista('tabla')}
    >
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
        <line x1="3" y1="9" x2="21" y2="9"></line>
        <line x1="3" y1="15" x2="21" y2="15"></line>
        <line x1="9" y1="3" x2="9" y2="21"></line>
        <line x1="15" y1="3" x2="15" y2="21"></line>
      </svg>
      Ver Inspecciones
    </button>
  </div>
</nav>

<style>
  nav {
    background: white;
    border-bottom: 1px solid #e2e8f0;
  }

  .nav-content {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    gap: 0.5rem;
    padding: 0.75rem 2rem;
  }

  .nav-btn {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.75rem 1.25rem;
    border: none;
    background: transparent;
    color: #64748b;
    font-size: 0.9375rem;
    font-weight: 500;
    border-radius: 0.5rem;
    cursor: pointer;
    transition: all 0.2s ease;
  }

  .nav-btn:hover {
    background: #f1f5f9;
    color: #1e40af;
  }

  .nav-btn.active {
    background: #eff6ff;
    color: #1e40af;
  }
</style>
