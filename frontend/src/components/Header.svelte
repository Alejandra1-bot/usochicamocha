<script>
  import { inspecciones } from '../stores/inspecciones.js';
  
  $: totalInspecciones = $inspecciones.length;
</script>

<header>
  <div class="header-content">
    <div class="header-title">
      <h1>Sistema de Inspecciones</h1>
      <span class="subtitle">UsoChicamocha</span>
    </div>
    <div class="stats">
      <div class="stat-item">
        <span class="stat-number">{totalInspecciones}</span>
        <span class="stat-label">Inspecciones</span>
      </div>
    </div>
  </div>
</header>

<style>
  header {
    background: linear-gradient(135deg, #1e40af 0%, #3b82f6 100%);
    color: white;
    padding: 1.5rem 2rem;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  }

  .header-content {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .header-title h1 {
    font-size: 1.75rem;
    font-weight: 700;
    margin-bottom: 0.25rem;
  }

  .subtitle {
    font-size: 0.875rem;
    opacity: 0.9;
  }

  .stats {
    display: flex;
    gap: 1.5rem;
  }

  .stat-item {
    text-align: center;
    background: rgba(255, 255, 255, 0.15);
    padding: 0.75rem 1.5rem;
    border-radius: 0.5rem;
  }

  .stat-number {
    display: block;
    font-size: 1.5rem;
    font-weight: 700;
  }

  .stat-label {
    font-size: 0.75rem;
    opacity: 0.9;
  }
</style>
