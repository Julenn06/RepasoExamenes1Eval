# 💻 Hardware - Conceptos Básicos

## 🖥️ Sistema Informático

**Definición**: Conjunto de elementos hardware y software que trabajan juntos para procesar datos y generar información útil.

**Componentes principales**:
- **Hardware** 🔧: Parte física (procesador, memoria, periféricos)
- **Software** 💿: Programas y sistemas operativos
- **Datos** 📊: Información procesada
- **Usuarios** 👤: Personas que interactúan con el sistema

---

## 🏗️ Arquitectura de von Neumann

**Características principales**:
- Unidad Central de Proceso (CPU)
- Memoria principal única para datos e instrucciones
- Unidades de entrada/salida
- Bus de datos compartido

**Componentes**:
1. **CPU** ⚡: Ejecuta instrucciones
2. **Memoria** 🧠: Almacena datos y programas
3. **E/S** 🔌: Comunica con el exterior
4. **Buses** 🚌: Conectan los componentes

---

## 🔲 Placa Base (Motherboard)

**Función**: Circuito impreso principal que conecta todos los componentes del ordenador.

**Elementos clave**:
- **Socket del procesador** 🎯: Donde se instala la CPU
- **Slots de RAM** 📍: Para módulos de memoria
- **Chipset** 🎛️: Controla comunicaciones (Northbridge y Southbridge)
- **Slots de expansión** 🎰: PCIe, PCI
- **Conectores** 🔗: SATA, USB, alimentación
- **BIOS/UEFI** ⚙️: Firmware de inicio

**Factor de forma común**: ATX, Micro-ATX, Mini-ITX

---

## ⚡ Procesador (CPU)

**Función**: Unidad central que ejecuta instrucciones y realiza cálculos.

**Componentes internos**:
- **UC (Unidad de Control)** 🎮: Coordina operaciones
- **ALU (Unidad Aritmético-Lógica)** 🧮: Realiza operaciones
- **Registros** 📝: Memoria ultrarrápida interna
- **Caché** 💾: Memoria rápida (L1, L2, L3)

**Características importantes**:
- **Frecuencia** ⏱️: GHz (velocidad de reloj)
- **Núcleos** 🔢: Cantidad de procesadores físicos
- **Hilos** 🧵: Procesamiento simultáneo
- **Arquitectura** 🏛️: x86, x64, ARM

**Fabricantes principales**: Intel, AMD

---

## 🧠 Memoria RAM

**Función**: Almacenamiento temporal y volátil de datos en ejecución.

**Características**:
- **Volátil** ⚠️: Pierde datos sin alimentación
- **Rápida** 🚀: Acceso directo y aleatorio
- **Tipos** 📌: DDR3, DDR4, DDR5
- **Capacidad** 📏: GB (4, 8, 16, 32...)
- **Frecuencia** 📡: MHz (velocidad de transferencia)

**Dual Channel** ⚡⚡: Uso de dos módulos para mejor rendimiento

---

## 💿 Memoria ROM y BIOS/UEFI

**ROM (Read-Only Memory)**:
- Memoria de solo lectura 🔒
- No volátil ✅
- Contiene el firmware básico

**BIOS (Basic Input/Output System)**:
- Firmware antiguo 📟
- Interfaz básica de texto
- Limitaciones con discos grandes

**UEFI (Unified Extensible Firmware Interface)**:
- Sucesor moderno de BIOS 🆕
- Interfaz gráfica 🖼️
- Arranque más rápido ⚡
- Soporte para discos grandes (GPT)
- Secure Boot 🔐

---

## 💾 Almacenamiento

### 🔵 Disco Duro (HDD)
- **Tecnología**: Mecánica (platos magnéticos) 💿
- **Capacidad**: Alta (TB) 📦
- **Velocidad**: Más lenta (RPM: 5400, 7200) 🐢
- **Precio**: Económico 💰
- **Uso**: Almacenamiento masivo

### ⚡ SSD (Solid State Drive)
- **Tecnología**: Memoria flash (sin partes móviles) 💫
- **Capacidad**: Menor que HDD
- **Velocidad**: Muy rápida 🚀
- **Tipos**: SATA, M.2, NVMe
- **Ventajas**: Sin ruido 🔇, resistente 💪, bajo consumo 🔋

**Interfaces**:
- **SATA** 📊: Hasta 600 MB/s
- **NVMe (M.2)** 🚀: Hasta 7000 MB/s

---

## 🎮 Tarjeta Gráfica (GPU)

**Función**: Procesa y genera imágenes para visualización.

**Tipos**:
- **Integrada** 🔸: En el procesador, básica
- **Dedicada** 💎: Tarjeta independiente, potente

**Componentes**:
- **GPU** 🎨: Procesador gráfico
- **VRAM** 🧠: Memoria dedicada
- **Conectores** 🔌: HDMI, DisplayPort, DVI

**Uso intensivo**: Gaming 🎮, diseño 3D 🎨, edición de vídeo 🎬, IA 🤖

**Fabricantes**: NVIDIA, AMD

---

## ⚡ Fuente de Alimentación (PSU)

**Función**: Convierte corriente alterna (AC) a continua (DC) y distribuye energía.

**Características**:
- **Potencia** 💪: Vatios (W) - 500W, 650W, 750W...
- **Certificación 80 Plus** ⭐: Eficiencia energética (Bronze, Silver, Gold, Platinum, Titanium)
- **Modular** 🔌: Cables removibles
- **Protecciones** 🛡️: OVP, UVP, OCP, SCP

**Conectores principales**:
- ATX 24 pines (placa base) 🔲
- CPU 4/8 pines ⚡
- PCIe 6/8 pines (GPU) 🎮
- SATA, Molex 💾

---

## ❄️ Refrigeración

**Objetivo**: Disipar el calor generado por componentes.

**Sistemas**:

### 🌬️ Refrigeración por aire
- **Disipador**: Metal conductor de calor 🔩
- **Ventilador**: Mueve el aire 💨
- **Pasta térmica**: Mejora transferencia de calor 🧴

### 💧 Refrigeración líquida
- **AIO (All-in-One)**: Sistema cerrado 🔄
- **Custom loop**: Sistema personalizado 🎨
- **Mejor rendimiento**: Para overclock 🚀

---

## 🖱️ Periféricos

### 📥 Entrada
- Teclado ⌨️
- Ratón 🖱️
- Scanner 📄
- Micrófono 🎤
- Webcam 📷
- Gamepad 🎮

### 📤 Salida
- Monitor 🖥️
- Impresora 🖨️
- Altavoces 🔊
- Auriculares 🎧

### 🔄 Entrada/Salida
- Pantalla táctil 📱
- Unidades de almacenamiento externas 💾
- Dispositivos USB multifunción 🔌

---

## 🔌 Conectores y Puertos

**USB (Universal Serial Bus)** 🔗:
- USB 2.0: 480 Mbps
- USB 3.0/3.1: 5-10 Gbps
- USB 3.2: 20 Gbps
- USB-C: Reversible, universal 🔄

**Otros**:
- **HDMI** 📺: Audio y vídeo digital
- **DisplayPort** 🖥️: Alto rendimiento gráfico
- **Ethernet (RJ45)** 🌐: Red cableada
- **Jack 3.5mm** 🎧: Audio analógico
- **Thunderbolt** ⚡: Alta velocidad (40 Gbps)

---

## 🔧 Ensamblaje de PC

**Pasos básicos**:
1. ⚡ Instalar CPU en el socket
2. 🧠 Colocar memoria RAM
3. 🔲 Montar placa base en caja
4. 🔌 Instalar fuente de alimentación
5. 💾 Conectar almacenamiento (HDD/SSD)
6. 🎮 Instalar tarjeta gráfica
7. 🔗 Conectar todos los cables
8. ❄️ Instalar refrigeración
9. ✅ Cerrar caja y conectar periféricos

**Precauciones** ⚠️:
- Descarga electrostática (usar pulsera antiestática) ⚡
- Aplicar pasta térmica correctamente 🧴
- Conectar alimentación correctamente 🔌
- Verificar compatibilidad de componentes ✔️

---

## 🛠️ Mantenimiento

**Preventivo** 🔍:
- Limpieza del polvo 🧹
- Verificar temperaturas 🌡️
- Actualizar drivers y BIOS 🔄
- Comprobar cables y conexiones 🔗

**Correctivo** 🔨:
- Diagnóstico de fallos 🔬
- Reemplazo de componentes dañados 🔧
- Reinstalación de software 💿

---

## 📈 Rendimiento y Optimización

**Factores que afectan el rendimiento** ⚡:
- Velocidad del procesador 🚀
- Cantidad de RAM 🧠
- Tipo de almacenamiento (SSD > HDD) 💾
- Tarjeta gráfica 🎮
- Refrigeración adecuada ❄️

**Mejoras** ⬆️:
- Ampliar RAM 📊
- Cambiar a SSD 💫
- Actualizar GPU 🎨
- Mejorar refrigeración 🌬️
- Overclock (con precaución) ⚠️
