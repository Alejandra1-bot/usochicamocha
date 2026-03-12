<script>
  import Header from './components/Header.svelte';
  import Navigation from './components/Navigation.svelte';
  import FormularioRegistro from './components/FormularioRegistro.svelte';
  import TablaInspecciones from './components/TablaInspecciones.svelte';
  import { inspecciones, cargarInspecciones } from './stores/inspecciones.js';
  import { onMount } from 'svelte';

  let vistaActual = 'registro';

  onMount(() => {
    cargarInspecciones();
  });

  function cambiarVista(vista) {
    vistaActual = vista;
  }
</script>

<main>
  <Header />
  <Navigation {vistaActual} on:cambiarVista={(e) => cambiarVista(e.detail)} />
  
  <div class="container">
    {#if vistaActual === 'registro'}
      <FormularioRegistro />
    {:else}
      <TablaInspecciones />
    {/if}
  </div>
</main>

<style>
  :global(*) {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
  }

  :global(body) {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    background-color: #f1f5f9;
    min-height: 100vh;
  }

  main {
    min-height: 100vh;
  }

  .container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 2rem;
  }
</style>
