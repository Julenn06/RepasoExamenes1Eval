# 📚 Proyectos de Repaso - Examen Acceso a Datos

Colección de 6 proyectos Java para practicar conceptos de **Acceso a Datos** con diferentes tecnologías y arquitecturas.

---

## 📁 Estructura General

Todos los proyectos siguen la **arquitectura MVC (Model-View-Controller)**:

```
src/
├── model/          ← Clases de datos (POJOs)
├── controller/     ← Lógica de negocio y acceso a datos
└── view/           ← Interfaz de usuario (Main.java)
```

---

## 🗂️ Proyectos

### 1️⃣ **Repaso01** - Firebase + XML/DAT Básico

**Tecnologías:**
- ☁️ Firebase Firestore (base de datos NoSQL en la nube)
- 📄 Archivos XML
- 💾 Archivos DAT (binarios)

**Funcionalidades:**
- Conexión a Firebase usando `serviceAccountKey.json`
- CRUD completo de alumnos en Firestore
- Lectura/escritura de archivos XML
- Manejo de archivos DAT
- Operaciones asíncronas con Firestore

**Archivos principales:**
- `controller/DBConnection.java` - Inicialización de Firebase
- `controller/AlumnoController.java` - Operaciones CRUD con Firestore
- `controller/XMLController.java` - Procesamiento de XML
- `controller/DATController.java` - Manejo de archivos DAT
- `model/Alumnos.java` - Modelo de datos
- `view/Main.java` - Menú interactivo

**Conceptos clave:**
- Firebase Admin SDK
- Operaciones asíncronas (CompletableFuture)
- Parsing XML con DOM
- Serialización/deserialización de objetos

---

### 2️⃣ **Repaso02** - Parsing XML Complejo con Firestore

**Tecnologías:**
- ☁️ Firebase Firestore
- 📄 XML con estructuras anidadas (Clientes → Ventas → Productos)

**Funcionalidades:**
- Parsing de XML con **múltiples niveles de anidación**
- Estructura Cliente → Ventas → Productos
- Búsquedas avanzadas:
  - Por ID de cliente
  - Por nombre de cliente
  - Por nombre de producto (busca en todas las ventas)
- Listado completo con jerarquía

**Archivos principales:**
- `controller/ReadAll.java` - Lee toda la estructura XML
- `controller/ReadByID.java` - Busca cliente por ID
- `controller/ReadByName.java` - Busca cliente por nombre
- `controller/ReadByProductName.java` - Busca producto en todas las ventas
- `model/Producto.java` - Modelo de producto
- `model/Venta.java` - Modelo de venta
- `view/Main.java` - Menú de consultas

**Archivo XML de ejemplo:**
```xml
<clientes>
  <cliente id="1">
    <nombre>Juan Pérez</nombre>
    <ventas>
      <venta id="101">
        <producto>Laptop</producto>
        <cantidad>2</cantidad>
      </venta>
    </ventas>
  </cliente>
</clientes>
```

**Conceptos clave:**
- Parsing XML jerárquico
- Navegación por nodos DOM
- Modelos relacionados (Cliente-Venta-Producto)

---

### 3️⃣ **Repaso03** - Gestión de Alumnos con Archivos DAT (Texto)

**Tecnologías:**
- 💾 Archivos DAT en formato texto plano
- 📝 Formato personalizado: `nombre;edad;fecha`

**Funcionalidades:**
- **CRUD completo** de alumnos en archivo DAT
- **Búsquedas:**
  - Por nombre exacto
  - Por nombre parcial (contiene)
- **Filtros:**
  - Por rango de edad
- **Exportación:**
  - A CSV (con escape de caracteres)
  - A XML (generado manualmente)
  - A DAT (copia de seguridad)

**Archivos principales:**
- `controller/AlumnoController.java` - Toda la lógica de negocio
  - `leerTodos()` - Lee todos los alumnos
  - `buscarPorNombre()` - Búsqueda exacta
  - `buscarPorNombreParcial()` - Búsqueda parcial
  - `filtrarPorEdad()` - Filtro por rango
  - `crear()`, `actualizar()`, `eliminar()` - CRUD
  - `exportarCSV()`, `exportarXML()`, `exportarDAT()` - Exportación
- `model/Alumnos.java` - Modelo con nombre, edad, fechaInscripcion
- `view/Main.java` - Menú interactivo con emojis

**Formato del archivo DAT:**
```
Juan Pérez;20;15/03/2005
María García;22;20/01/2003
```

**Conceptos clave:**
- Lectura/escritura de archivos con NIO (Files API)
- Parsing manual de texto (split, trim)
- SimpleDateFormat para fechas
- Manejo de excepciones I/O
- Escape de caracteres para CSV y XML

---

### 4️⃣ **Repaso04** - Gestión de Alumnos con XML (DOM)

**Tecnologías:**
- 📄 Archivos XML con parsing DOM
- 🔄 Transformación XML (javax.xml.transform)

**Funcionalidades:**
- **CRUD completo** de alumnos en XML
- **Búsquedas:**
  - Por nombre exacto
  - Por nombre parcial
- **Filtros:**
  - Por rango de edad
- **Exportación:**
  - A CSV
  - A DAT (formato texto)

**Archivos principales:**
- `controller/AlumnoController.java` - Lógica con DOM parsing
  - Método privado `guardarTodos()` - Regenera el XML completo
  - Método privado `getElementText()` - Extrae texto de elementos
- `model/Alumnos.java` - Mismo modelo que Repaso03
- `view/Main.java` - Interfaz similar a Repaso03

**Estructura del XML:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<alumnos>
  <alumno>
    <nombre>Juan Pérez</nombre>
    <edad>20</edad>
    <fechaInscripcion>15/03/2005</fechaInscripcion>
  </alumno>
</alumnos>
```

**Conceptos clave:**
- DocumentBuilder y DocumentBuilderFactory
- Creación y manipulación de nodos DOM
- Transformer para escribir XML
- OutputKeys para formateo (INDENT, ENCODING)

---

### 5️⃣ **Repaso05** - MVC Avanzado con Firebase y Relaciones

**Tecnologías:**
- ☁️ Firebase Firestore
- 🔗 Relaciones entre colecciones (Alumnos ↔ Cursos)

**Funcionalidades:**
- **CRUD de Alumnos** en Firestore
- **CRUD de Cursos** en Firestore
- **Relaciones:**
  - Asignar alumnos a cursos
  - Listar alumnos de un curso
  - Listar cursos de un alumno
- **Exportación:**
  - Alumnos a CSV
  - Alumnos a JSON
  - Cursos a JSON

**Archivos principales:**
- `controller/DBConnection.java` - Inicialización Firebase
- `controller/AlumnoController.java` - CRUD de alumnos
- `controller/CursoController.java` - CRUD de cursos
- `controller/AlumnoCursoController.java` - Gestión de relaciones
- `controller/ExportController.java` - Exportación de datos
- `model/Alumnos.java` - Con campos adicionales (email, teléfono)
- `model/Curso.java` - Nombre, descripción, créditos
- `view/Main.java` - Menú con 7 submenús

**Estructura Firestore:**
```
firestore/
├── alumnos/
│   └── {id} → {nombre, edad, email, telefono}
├── cursos/
│   └── {id} → {nombre, descripcion, creditos}
└── alumno_curso/
    └── {id} → {alumnoId, cursoId}
```

**Conceptos clave:**
- Arquitectura MVC perfecta (10/10)
- Relaciones muchos-a-muchos en Firestore
- Consultas con filtros (whereEqualTo)
- Exportación múltiple formato

---

### 6️⃣ **Repaso06** - Sistema Académico Completo (SUPER PROYECTO)

**Tecnologías:**
- ☁️ Firebase Firestore
- 🏗️ Arquitectura MVC compleja con múltiples entidades

**Funcionalidades:**
Este es el proyecto **MÁS COMPLETO** con **5 modelos** y **70+ operaciones**:

#### **Entidades:**
1. **Alumno** (15 campos) - Datos personales completos
2. **Profesor** (15 campos) - Información profesional
3. **Curso** (14 campos) - Cursos académicos
4. **Asignatura** (11 campos) - Materias del curso
5. **Matricula** (13 campos) - Relación alumno-asignatura-curso

#### **Controladores:**
- `AlumnoController` - 12 operaciones (CRUD + búsquedas + estadísticas)
- `ProfesorController` - 12 operaciones
- `CursoController` - 11 operaciones
- `AsignaturaController` - 12 operaciones
- `MatriculaController` - 15 operaciones (relaciones complejas)
- `EstadisticasController` - 8 operaciones (análisis de datos)

**Operaciones destacadas:**
- 📊 Estadísticas completas (promedios, totales, rankings)
- 🔍 Búsquedas avanzadas (por múltiples criterios)
- 📈 Análisis de rendimiento académico
- 🎓 Gestión de calificaciones
- 👥 Relaciones complejas entre entidades
- 📅 Historial académico completo

**Archivos principales:**
```
controller/
├── DBConnection.java          ← Inicialización Firebase
├── AlumnoController.java      ← 12 métodos
├── ProfesorController.java    ← 12 métodos
├── CursoController.java       ← 11 métodos
├── AsignaturaController.java  ← 12 métodos
├── MatriculaController.java   ← 15 métodos
└── EstadisticasController.java ← 8 métodos

model/
├── Alumno.java        ← 15 campos
├── Profesor.java      ← 15 campos
├── Curso.java         ← 14 campos
├── Asignatura.java    ← 11 campos
└── Matricula.java     ← 13 campos

view/
└── Main.java          ← 7 menús interactivos
```

**Estructura de menús:**
```
1. Gestión de Alumnos (12 opciones)
2. Gestión de Profesores (12 opciones)
3. Gestión de Cursos (11 opciones)
4. Gestión de Asignaturas (12 opciones)
5. Gestión de Matrículas (15 opciones)
6. Estadísticas y Reportes (8 opciones)
7. Salir
```

**Conceptos clave:**
- Sistema académico completo y realista
- Relaciones múltiples entre entidades
- Validaciones complejas de negocio
- Cálculos estadísticos
- Arquitectura escalable
- 70+ operaciones diferentes

---

## 🛠️ Tecnologías Utilizadas

### **Lenguaje y Build:**
- ☕ Java 21
- 🔨 Maven 3.11.0

### **Bases de Datos:**
- ☁️ Firebase Firestore (Repaso01, 02, 05, 06)
- 📄 Archivos XML (Repaso01, 02, 04)
- 💾 Archivos DAT (Repaso01, 03)

### **Librerías:**
- `firebase-admin` 9.2.0 - SDK de Firebase
- `javax.xml.parsers` - Parsing XML con DOM
- `javax.xml.transform` - Transformación XML
- `java.nio.file` - Manejo de archivos moderno
- `java.text.SimpleDateFormat` - Formato de fechas

### **Patrones de Diseño:**
- 🏗️ MVC (Model-View-Controller)
- 📦 DAO (Data Access Object) implícito en controllers
- 🔌 Singleton (DBConnection)

---

## 🚀 Cómo Ejecutar

### **Requisitos previos:**
```bash
# Java 21
java -version

# Maven 3.11+
mvn -version
```

### **Para proyectos con Firebase (01, 02, 05, 06):**

1. Obtener `serviceAccountKey.json` desde Firebase Console
2. Colocar el archivo en la raíz del proyecto
3. Compilar y ejecutar:

```bash
cd RepasoXX
mvn clean compile
mvn exec:java -Dexec.mainClass="view.Main"
```

### **Para proyectos sin Firebase (03, 04):**

```bash
cd RepasoXX
mvn clean compile
mvn exec:java -Dexec.mainClass="view.Main"
```

---

## 📖 Conceptos de Examen Cubiertos

### **1. Acceso a Datos:**
- ✅ CRUD completo (Create, Read, Update, Delete)
- ✅ Bases de datos NoSQL (Firestore)
- ✅ Archivos de texto (DAT)
- ✅ Archivos XML (DOM parsing)

### **2. Arquitectura:**
- ✅ Patrón MVC
- ✅ Separación de capas
- ✅ Bajo acoplamiento
- ✅ Alta cohesión

### **3. Manejo de Archivos:**
- ✅ NIO (java.nio.file)
- ✅ Lectura/escritura de texto
- ✅ Parsing de formatos personalizados
- ✅ Exportación múltiple formato

### **4. XML:**
- ✅ Parsing con DOM
- ✅ Creación de documentos XML
- ✅ Transformación XML
- ✅ Navegación por nodos

### **5. Firebase:**
- ✅ Inicialización del SDK
- ✅ Operaciones CRUD
- ✅ Consultas con filtros
- ✅ Operaciones asíncronas

### **6. Búsquedas y Filtros:**
- ✅ Búsqueda exacta
- ✅ Búsqueda parcial (contains)
- ✅ Filtros por rango
- ✅ Consultas complejas

### **7. Exportación:**
- ✅ A CSV
- ✅ A XML
- ✅ A JSON
- ✅ Copias de seguridad

---

## 📝 Notas Importantes

- **Todos los proyectos compilan sin errores** ✅
- **Arquitectura MVC validada** en los 6 proyectos
- **Código comentado** para facilitar el estudio
- **Menús interactivos** con emojis para mejor UX
- **Formato de fecha consistente:** `dd/MM/yyyy`
- **Encoding UTF-8** en todos los archivos

---

## 🔗 Estructura de Directorios

```
RepasoExamenAD/
├── Repaso01/                    ← Firebase + XML/DAT
│   ├── pom.xml
│   ├── serviceAccountKey.json
│   ├── alumnos.xml
│   └── src/
│       ├── controller/
│       ├── model/
│       └── view/
│
├── Repaso02/                    ← XML Complejo + Firestore
│   ├── pom.xml
│   ├── serviceAccountKey.json
│   ├── clientes.xml
│   └── src/
│       ├── controller/
│       ├── model/
│       └── view/
│
├── Repaso03/                    ← Archivos DAT (Texto)
│   ├── pom.xml
│   ├── alumnos.dat
│   └── src/
│       ├── controller/
│       ├── model/
│       └── view/
│
├── Repaso04/                    ← XML con DOM
│   ├── pom.xml
│   ├── alumnos.xml
│   └── src/
│       ├── controller/
│       ├── model/
│       └── view/
│
├── Repaso05/                    ← Firebase Relacional
│   ├── pom.xml
│   ├── serviceAccountKey.json
│   └── src/
│       ├── controller/
│       ├── model/
│       └── view/
│
└── Repaso06/                    ← Sistema Completo
    ├── pom.xml
    ├── serviceAccountKey.json
    ├── README.md              ← Documentación específica
    └── src/
        ├── controller/        ← 6 controladores
        ├── model/             ← 5 modelos
        └── view/              ← 1 vista con 7 menús
```