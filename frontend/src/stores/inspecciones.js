// Importamos la función 'writable' de Svelte
// writable es como una variable especial que puede cambiar y avisa cuando cambia
import { writable } from 'svelte/store';

// URL del backend - aquí se conecta el frontend con el backend
// Si está null, usamos datos de prueba (sin backend real)
// const API_URL = 'http://localhost:8080/api/inspecciones';
const API_URL = null;

// Datos de prueba - son datos ficticios para probar la app
// No vienen de una base de datos real
let datosPrueba = [
  { id: 1, numeroMaquina: 'Máquina A1', nombreOperador: 'Juan Pérez', horometro: 1500.5, estado: 'Bueno', observaciones: 'Todo en orden', fechaInspeccion: '2026-03-01', horaInspeccion: '08:30', ultimaActualizacion: '2026-03-01 08:30' },
  { id: 2, numeroMaquina: 'Máquina B2', nombreOperador: 'María López', horometro: 2300.0, estado: 'Regular', observaciones: 'Revisar filtros', fechaInspeccion: '2026-03-11', horaInspeccion: '09:15', ultimaActualizacion: '2026-03-11 09:15' },
  { id: 3, numeroMaquina: 'Máquina C3', nombreOperador: 'Carlos Gómez', horometro: 800.3, estado: 'Bueno', observaciones: 'Sin observaciones', fechaInspeccion: '2026-03-12', horaInspeccion: '10:00', ultimaActualizacion: '2026-03-12 10:00' },
];

// Función para generar el siguiente ID disponible (sin huecos)
function generarSiguienteId() {
  if (datosPrueba.length === 0) return 1;
  // Busca el menor ID que no exista
  const ids = datosPrueba.map(d => d.id).sort((a, b) => a - b);
  for (let i = 1; i <= ids.length + 1; i++) {
    if (!ids.includes(i)) return i;
  }
  return ids.length + 1;
}

// Creamos un "almacén" (store) para guardar las inspecciones
// Es como una variable global que pueden usar todos los componentes
export const inspecciones = writable([]);

// Store para saber si está cargando datos
export const cargando = writable(false);

// Store para guardar errores
export const error = writable(null);

// Función para CARGAR todas las inspecciones
export async function cargarInspecciones() {
  // Ponemos cargando en true mientras esperamos
  cargando.set(true);
  // Limpiamos errores anteriores
  error.set(null);
  
  // Si no hay backend (API_URL es null), usamos datos de prueba
  if (!API_URL) {
    // Simulamos una espera de 0.5 segundos (como si cargara de internet)
    await new Promise(resolve => setTimeout(resolve, 500));
    // Ponemos los datos de prueba en el store
    inspecciones.set([...datosPrueba]);
    // Terminamos de cargar
    cargando.set(false);
    return;
  }
  
  // Si hay backend, intentamos traer datos reales
  try {
    // Pedimos datos al servidor
    const response = await fetch(API_URL);
    // Convertimos la respuesta a JSON
    const data = await response.json();
    
    // Si todo salió bien, guardamos los datos
    if (data.success) {
      inspecciones.set(data.data);
    } else {
      // Si hubo error del servidor, lo guardamos
      error.set(data.message);
    }
  } catch (e) {
    // Si no pudimos conectar al servidor
    error.set('Error al conectar con el servidor');
    console.error(e);
  } finally {
    // Siempre terminamos de cargar, aunque haya error o no
    cargando.set(false);
  }
}

// Función para CREAR una nueva inspección
export async function crearInspeccion(inspeccion) {
  // Indicamos que estamos ocupados
  cargando.set(true);
  error.set(null);
  
  // Si no hay backend, usamos datos de prueba
  if (!API_URL) {
    await new Promise(resolve => setTimeout(resolve, 500));
    // Creamos un nuevo ID usando la función que busca el siguiente disponible
    const nuevoId = generarSiguienteId();
    // Obtenemos la fecha y hora actual automáticamente
    const ahora = new Date();
    const fecha = ahora.toISOString().split('T')[0];
    const hora = ahora.toTimeString().split(' ')[0].substring(0, 5);
    // Creamos el nuevo registro con todos los campos
    const nuevoRegistro = {
      id: nuevoId,
      numeroMaquina: inspeccion.maquina,
      nombreOperador: inspeccion.operador,
      horometro: inspeccion.horometro,
      estado: inspeccion.estado,
      observaciones: inspeccion.observaciones,
      fechaInspeccion: fecha,
      horaInspeccion: hora,
      ultimaActualizacion: fecha + ' ' + hora
    };
    // Agregamos la nueva inspección a los datos de prueba
    datosPrueba.push(nuevoRegistro);
    // Actualizamos el store con los datos formateados
    const datosFormateados = datosPrueba.map(d => ({
      id: d.id,
      numeroMaquina: d.numeroMaquina,
      nombreOperador: d.nombreOperador,
      horometro: d.horometro,
      estadoFreno: d.estadoFreno,
      estadoLuces: d.estadoLuces,
      nivelAceite: d.nivelAceite,
      observaciones: d.observaciones,
      fechaInspeccion: d.fechaInspeccion,
      horaInspeccion: d.horaInspeccion,
      ultimaActualizacion: d.fechaInspeccion + ' ' + d.horaInspeccion
    }));
    inspecciones.set(datosFormateados);
    cargando.set(false);
    return { success: true, message: 'Inspección creada (modo prueba)' };
  }
  
  // Si hay backend, enviamos los datos al servidor
  try {
    const response = await fetch(API_URL, {
      method: 'POST', // Método para crear
      headers: {
        'Content-Type': 'application/json', // Decimos que enviamos JSON
      },
      body: JSON.stringify(inspeccion), // Convertimos el objeto a texto
    });
    
    const data = await response.json();
    
    if (data.success) {
      // Si se creó bien, recargamos la lista
      await cargarInspecciones();
      return { success: true, message: data.message };
    } else {
      return { success: false, message: data.message };
    }
  } catch (err) {
    error.set('Error al crear la inspeccion');
    return { success: false, message: 'Error al conectar con el servidor' };
  } finally {
    cargando.set(false);
  }
}

// Función para ELIMINAR una inspección
export async function eliminarInspeccion(id) {
  cargando.set(true);
  error.set(null);
  
  // Si no hay backend, eliminamos de los datos de prueba
  if (!API_URL) {
    await new Promise(resolve => setTimeout(resolve, 500));
    const index = datosPrueba.findIndex(i => i.id === id);
    if (index > -1) {
      datosPrueba.splice(index, 1); // Eliminamos el elemento
    }
    inspecciones.set([...datosPrueba]);
    cargando.set(false);
    return { success: true, message: 'Inspección eliminada (modo prueba)' };
  }
  
  // Si hay backend, pedimos al servidor que elimine
  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: 'DELETE', // Método para eliminar
    });
    
    const data = await response.json();
    
    if (data.success) {
      await cargarInspecciones();
      return { success: true, message: data.message };
    } else {
      return { success: false, message: data.message };
    }
  } catch (err) {
    error.set('Error al eliminar la inspeccion');
    return { success: false, message: 'Error al conectar con el servidor' };
  } finally {
    cargando.set(false);
  }
}
