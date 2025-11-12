# 📚 Guía Completa: HILOS vs PROCESOS en Java

## 📑 Índice
1. [Programa de Hilos](#-programa-de-hilos)
2. [Programa de Procesos](#-programa-de-procesos)
3. [Resumen de Diferencias](#-resumen-de-diferencias-clave)

---

## 🧵 PROGRAMA DE HILOS
### Ventana con Cronómetro y Reloj

Este programa demuestra el uso de **hilos concurrentes** en una aplicación GUI (Swing). Contiene 3 archivos principales:

---

### 📄 **1. `HiloHora.java` - Hilo que muestra la hora actual**

#### **Características:**
```java
public class HiloHora extends Thread
```

- ✅ **Extiende `Thread`** directamente
- ✅ Actualiza la hora cada **500ms**
- ✅ Tiene prioridad **normal** (5)

#### **Funcionamiento:**

**Constructor:**
```java
public HiloHora(JLabel textoHora, JLabel estado) {
    setName("<<hora>>");              // Nombre del hilo
    setPriority(NORM_PRIORITY);       // Prioridad = 5
    this.textoHora = textoHora;       // Label para mostrar hora
    this.estado = estado;             // Label para estado
}
```

**Método `run()` - El corazón del hilo:**
```java
@Override
public void run() {
    enEjecucion = true;
    estado.setText("El hilo " + getName() + " ha comenzado");
    
    while (enEjecucion) {
        Date date = new Date();                          // Hora actual
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String horaActual = sdf.format(date);            // Formato: "14:30:25"
        textoHora.setText(horaActual);                   // Actualiza la interfaz
        
        try {
            Thread.sleep(500);                           // Espera medio segundo
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

**Método `parar()`:**
```java
public void parar() {
    enEjecucion = false;  // Detiene el bucle while
    estado.setText("El hilo " + getName() + " ha finalizado");
}
```

#### **🔑 Puntos Clave:**
- El hilo corre en **segundo plano** constantemente
- Se actualiza cada **0.5 segundos** (no cada segundo) para mayor precisión
- Usa `Thread.sleep()` para no consumir demasiada CPU

---

### 📄 **2. `HiloCronometro.java` - Cuenta regresiva con pausas**

#### **Características:**
```java
public class HiloCronometro implements Runnable
```

- ✅ **Implementa `Runnable`** (forma recomendada)
- ✅ Cuenta regresiva de **1h 30min** (5400 segundos)
- ✅ Permite **pausar/reanudar**
- ✅ Permite **detener** antes de tiempo
- ✅ Usa **sincronización avanzada** con `wait()`/`notifyAll()`

#### **Variables importantes:**
```java
private boolean enPausa;                    // ¿Está pausado?
private boolean terminado = false;          // ¿Ha terminado?
private final Object bloqueoPausa = new Object(); // Monitor para sincronización
```

#### **Método `run()` - Lógica principal:**

**1. Inicialización:**
```java
int total = 5400;  // 1h 30min en segundos
long inicio = System.currentTimeMillis();  // Momento de inicio
long pausaAcum = 0L;  // Tiempo total acumulado en pausas
long inicioPausaLocal = 0L;
```

**2. Bucle principal:**
```java
while (!terminado) {
    // Manejo de pausas...
    // Cálculo del tiempo restante...
    // Actualización visual...
}
```

**3. Manejo de PAUSAS (sincronización compleja):**
```java
if (enPausa) {
    inicioPausaLocal = System.currentTimeMillis();  // Marca cuando empieza la pausa
    
    synchronized (bloqueoPausa) {
        while (enPausa) {
            bloqueoPausa.wait();  // ⏸️ BLOQUEA el hilo hasta que se reanude
        }
    }
    
    // Cuando se reanuda, calcula cuánto tiempo estuvo pausado
    pausaAcum += System.currentTimeMillis() - inicioPausaLocal;
}
```

**❓ ¿Por qué acumular tiempo pausado?**
Para descontarlo del tiempo total y que la cuenta regresiva sea **precisa**.

**4. Cálculo del tiempo restante:**
```java
long ahora = System.currentTimeMillis();
long trans = (ahora - inicio - pausaAcum);  // Tiempo transcurrido (sin pausas)
int restante = (int) Math.max(0, total - (trans / 1000));  // Segundos restantes
```

**5. Formateo del tiempo:**
```java
private String formatear(int tsegundos) {
    int hh = (tsegundos / 3600);                      // Horas
    int mm = ((tsegundos - hh * 3600) / 60);          // Minutos
    int ss = tsegundos - (hh * 3600 + mm * 60);       // Segundos
    return String.format("%02d:%02d:%02d", hh, mm, ss); // "01:30:00"
}
```

**6. Actualización frecuente:**
```java
lblCronometro.setText(formatear(restante));  // Actualiza la interfaz
Thread.sleep(200);  // Se actualiza cada 0.2 segundos (más fluido)
```

#### **Métodos de control:**

**`pausaReanudar()` - Alterna entre pausar y reanudar:**
```java
public void pausaReanudar() {
    if (terminado) return;
    
    enPausa = !enPausa;  // Cambia el estado
    
    if (enPausa) {
        btnPausarReanudar.setText("Reanudar");
    } else {
        btnPausarReanudar.setText("Pausar");
        synchronized (bloqueoPausa) {
            bloqueoPausa.notifyAll();  // ▶️ DESPIERTA al hilo bloqueado
        }
    }
}
```

**`parar()` - Detiene el cronómetro:**
```java
public void parar() {
    terminado = true;
    enPausa = false;
    synchronized (bloqueoPausa) {
        bloqueoPausa.notifyAll();  // Si estaba pausado, lo despierta para terminar
    }
}
```

#### **🔑 Puntos Clave:**
- **`wait()`**: Bloquea el hilo hasta recibir `notifyAll()`
- **`notifyAll()`**: Despierta todos los hilos bloqueados en ese objeto
- **`synchronized`**: Asegura acceso exclusivo al objeto (evita race conditions)
- **Acumulación de pausas**: Mantiene la precisión del cronómetro

---

### 📄 **3. `Ventana.java` - Interfaz gráfica (coordinador)**

#### **Componentes visuales:**
- 🕐 **Cronómetro grande**: Muestra "01:30:00"
- ⏰ **Hora actual**: Esquina superior derecha
- 🎛️ **3 Botones**: Iniciar, Pausar/Reanudar, Parar
- 📊 **Label de estado**: Muestra mensajes informativos

#### **Lógica de los botones:**

**Botón INICIAR:**
```java
btnIniciar.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        // 1. Crear instancia del Runnable
        cronometro = new HiloCronometro(lblEstado, btnIniciar, btnParar, 
                                        btnPausarReanudar, lblCronometro);
        
        // 2. Envolver en Thread
        Thread hiloCronometro = new Thread(cronometro);
        
        // 3. Configurar
        hiloCronometro.setName("<<reloj>>");
        hiloCronometro.setPriority(Thread.NORM_PRIORITY);
        
        // 4. ¡Arrancar!
        hiloCronometro.start();
    }
});
```

**Botón PAUSAR/REANUDAR:**
```java
btnPausarReanudar.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        cronometro.pausaReanudar();  // Delega al objeto cronometro
    }
});
```

**Botón PARAR:**
```java
btnParar.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        cronometro.parar();  // Marca como terminado
    }
});
```

**Inicio automático del reloj:**
```java
// En el constructor de Ventana
HiloHora hiloHora = new HiloHora(lblHoraActual, lblEstado);
hiloHora.start();  // ¡El reloj empieza automáticamente al abrir la ventana!
```

#### **Estado inicial de botones:**
```java
btnParar.setEnabled(false);          // Deshabilitado
btnPausarReanudar.setEnabled(false); // Deshabilitado
// Solo "Iniciar" está habilitado al principio
```

#### **🔑 Puntos Clave:**
- **Dos hilos corriendo simultáneamente**: Cronómetro + Reloj
- Ambos hilos **comparten** los componentes gráficos (`JLabel`)
- La interfaz se actualiza desde los hilos (Swing permite esto en este caso simple)
- Los botones controlan el estado del cronómetro mediante llamadas a métodos

---

## 🔄 PROGRAMA DE PROCESOS
### Comunicación Padre-Hijo

Este programa demuestra la **comunicación entre procesos independientes** usando streams (stdin/stdout).

---

### 📄 **1. `Proceso.java` - Proceso PADRE**

#### **Funcionamiento paso a paso:**

**1. Crear el proceso hijo:**
```java
ProcessBuilder pb = new ProcessBuilder("java", "procesos.SumadorRestador");
pb.directory(new File("bin"));  // Directorio donde están las clases compiladas
pb.redirectOutput(new File("salida.txt"));  // Redirige stdout del hijo a archivo
Process proces = pb.start();  // ¡Inicia el proceso hijo!
```

**2. Mostrar información del proceso:**
```java
System.out.println("PID: " + proces.pid());  
// Ejemplo: PID: 12345

System.out.println("PID Padre: " + proces.toHandle().parent().get().pid());
// Ejemplo: PID Padre: 11111
```

**3. Comunicación PADRE → HIJO** (a través de stdin):
```java
Scanner teclado = new Scanner(System.in);

// Pide al usuario: ¿Sumar (1) o Restar (2)?
do {
    System.out.println("¿Que quieres hacer?\n1-. Sumar\n2-. Restar");
    opcion = teclado.nextInt();
    
    if(opcion == 1 || opcion == 2) {
        // Escribe en el stdin del hijo
        proces.getOutputStream().write((opcion + "\n").getBytes());
        proces.getOutputStream().flush();  // ¡Envía inmediatamente!
    }
} while (opcion != 1 && opcion != 2);

// Envía el primer operador
System.out.println("Operador 1:");
operador1 = teclado.nextInt();
proces.getOutputStream().write((operador1 + "\n").getBytes());
proces.getOutputStream().flush();

// Envía el segundo operador
System.out.println("Operador 2:");
operador2 = teclado.nextInt();
proces.getOutputStream().write((operador2 + "\n").getBytes());
proces.getOutputStream().flush();
```

#### **🔑 Puntos Clave:**
- `ProcessBuilder`: Crea y configura procesos
- `proces.getOutputStream()`: Es el **stdin del proceso hijo**
- `flush()`: Fuerza el envío inmediato de datos
- El padre envía **3 datos**: opción, operador1, operador2
- La salida del hijo va a `salida.txt`, no a la consola del padre

---

### 📄 **2. `SumadorRestador.java` - Proceso HIJO**

#### **Funcionamiento:**

```java
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);  // Lee desde stdin
    
    // Recibe los 3 datos del padre
    int opcion = sc.nextInt();    // 1 = suma, 2 = resta
    int operador1 = sc.nextInt(); // Primer número
    int operador2 = sc.nextInt(); // Segundo número
    
    int resultado = 0;
    
    // Realiza la operación
    if(opcion == 1)
        resultado = operador1 + operador2;  // SUMA
    else 
        resultado = operador1 - operador2;  // RESTA
    
    // Muestra el resultado
    System.out.println("Resultado:" + resultado);
    
    sc.close();
}
```

#### **¿Dónde va la salida?**
```
System.out.println("Resultado:" + resultado);
```
Esta línea escribe en **stdout**, que el padre redirigió a `salida.txt`.

**Contenido de `salida.txt` (ejemplo):**
```
Resultado:15
```

#### **🔑 Puntos Clave:**
- El hijo es un **programa Java completamente independiente**
- Lee datos del **stdin** (enviados por el padre)
- Escribe en **stdout** (redirigido a archivo)
- No hay comunicación directa: todo mediante **streams**

---

### 🔄 **Flujo de Comunicación Completo**

```
┌─────────────────┐                    ┌──────────────────┐
│  Proceso.java   │                    │ SumadorRestador  │
│    (PADRE)      │                    │     (HIJO)       │
└─────────────────┘                    └──────────────────┘
         │                                      │
         │  1. Crear proceso hijo               │
         │─────────────────────────────────────>│
         │                                      │
         │  2. Envía opción (1 o 2)            │
         │─────────────────────────────────────>│ sc.nextInt()
         │                                      │
         │  3. Envía operador1                 │
         │─────────────────────────────────────>│ sc.nextInt()
         │                                      │
         │  4. Envía operador2                 │
         │─────────────────────────────────────>│ sc.nextInt()
         │                                      │
         │                                      │ Calcula resultado
         │                                      │
         │                                      │ System.out.println()
         │                                      │       │
         │                                      │       ▼
         │                                    ┌──────────────┐
         │                                    │ salida.txt   │
         │                                    │ Resultado:15 │
         │                                    └──────────────┘
```

---

## 📋 RESUMEN DE DIFERENCIAS CLAVE

### **HILOS vs PROCESOS**

| Característica | 🧵 HILOS | 🔄 PROCESOS |
|----------------|---------|------------|
| **Definición** | Unidades de ejecución dentro de un proceso | Programas independientes |
| **Memoria** | ✅ Comparten memoria | ❌ Memoria independiente |
| **Comunicación** | Variables compartidas, sincronización (`wait`/`notify`) | Streams (stdin/stdout), archivos, sockets |
| **Creación** | `Thread.start()` o `new Thread(Runnable).start()` | `ProcessBuilder.start()` |
| **Overhead** | ⚡ Ligero (rápido) | 🐌 Pesado (lento) |
| **Aislamiento** | ❌ Comparten recursos | ✅ Totalmente aislados |
| **Fallo** | Un fallo puede afectar toda la aplicación | Un proceso no afecta a otros |
| **Sincronización** | `synchronized`, `wait()`, `notify()`, `Lock` | Pipes, archivos, sockets, memoria compartida |
| **Ejemplo uso** | Cronómetro + reloj en misma app | Calculadora como proceso externo |

---

### **En los ejemplos del código:**

#### **🧵 HILOS (Ventana):**
- ✅ **Dos hilos** corriendo en **la misma aplicación** (JFrame)
- ✅ Comparten componentes gráficos (`JLabel`, botones)
- ✅ Sincronización con `wait()`/`notifyAll()` para pausar/reanudar
- ✅ Comunicación directa (llamadas a métodos: `pausaReanudar()`, `parar()`)
- ✅ Ligero y eficiente
- ✅ Perfecto para tareas concurrentes en la misma aplicación

#### **🔄 PROCESOS (Sumador/Restador):**
- ✅ **Dos programas Java** completamente separados
- ✅ Comunicación mediante **streams** (`getOutputStream`/`getInputStream`)
- ✅ Padre controla al hijo pero **no comparten memoria**
- ✅ Output redirigido a archivo (`salida.txt`)
- ✅ Aislamiento total: si el hijo falla, el padre sigue corriendo
- ✅ Útil para ejecutar programas externos o separar responsabilidades

---

## 🎯 CONCEPTOS IMPORTANTES

### **Sincronización de Hilos:**

```java
// Objeto monitor
private final Object bloqueoPausa = new Object();

// Pausar (bloquear el hilo)
synchronized (bloqueoPausa) {
    while (enPausa) {
        bloqueoPausa.wait();  // ⏸️ Duerme hasta recibir notify
    }
}

// Reanudar (despertar el hilo)
synchronized (bloqueoPausa) {
    bloqueoPausa.notifyAll();  // ▶️ Despierta todos los hilos esperando
}
```

### **Creación de Hilos:**

**Forma 1: Extendiendo Thread**
```java
class MiHilo extends Thread {
    public void run() { /* código */ }
}
MiHilo hilo = new MiHilo();
hilo.start();
```

**Forma 2: Implementando Runnable** (⭐ RECOMENDADO)
```java
class MiTarea implements Runnable {
    public void run() { /* código */ }
}
Thread hilo = new Thread(new MiTarea());
hilo.start();
```

**Forma 3: Lambda** (⭐ MODERNO)
```java
Thread hilo = new Thread(() -> {
    /* código */
});
hilo.start();
```

### **Comunicación entre Procesos:**

**Padre → Hijo (stdin):**
```java
proces.getOutputStream().write(dato.getBytes());
proces.getOutputStream().flush();
```

**Hijo → Padre (stdout):**
```java
BufferedReader reader = new BufferedReader(
    new InputStreamReader(proces.getInputStream())
);
String linea = reader.readLine();
```

**Redirigir a archivo:**
```java
pb.redirectOutput(new File("salida.txt"));
pb.redirectError(new File("errores.txt"));
```

---

## 📚 CONCLUSIÓN

### **Usa HILOS cuando:**
- ✅ Necesitas tareas concurrentes en la **misma aplicación**
- ✅ Requieres **compartir datos** entre tareas
- ✅ Buscas **rendimiento** (bajo overhead)
- ✅ Ejemplos: GUI responsive, servidores web, procesamiento paralelo

### **Usa PROCESOS cuando:**
- ✅ Necesitas **aislamiento** total
- ✅ Quieres ejecutar **programas externos**
- ✅ Requieres **seguridad** (un fallo no afecta a otros)
- ✅ Ejemplos: ejecutar scripts, sandboxing, aplicaciones modulares

---

**Autor:** Daniel Alonso Mendez  
**Fecha:** Noviembre 2025  
**Tema:** Programación de Servicios y Procesos (PSP)
