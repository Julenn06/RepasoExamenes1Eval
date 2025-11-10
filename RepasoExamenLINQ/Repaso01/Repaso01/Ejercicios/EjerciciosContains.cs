using Repaso01.Models;

namespace Repaso01.Ejercicios;

public static class EjerciciosContains
{
    public static void Ejecutar(List<Estudiante> estudiantes, List<Producto> productos)
    {
        Console.WriteLine("\n═══════════════════════════════════════════════════════");
        Console.WriteLine("EJERCICIOS: CONTAINS()");
        Console.WriteLine("═══════════════════════════════════════════════════════\n");

        // Ejercicio 1: ¿Existe un estudiante con ID 3?
        Console.WriteLine("Ejercicio 1: ¿Existe un estudiante con ID 3?");
        var idsEstudiantes = estudiantes.Select(e => e.Id).ToList();
        bool existeId3 = idsEstudiantes.Contains(3);
        Console.WriteLine($"Respuesta: {(existeId3 ? "Si" : "No")}\n");

        // Ejercicio 2: ¿Hay algún producto llamado "Laptop"?
        Console.WriteLine("Ejercicio 2: ¿Existe un producto llamado 'Laptop'?");
        var nombresProductos = productos.Select(p => p.Nombre).ToList();
        bool existeLaptop = nombresProductos.Contains("Laptop");
        Console.WriteLine($"Respuesta: {(existeLaptop ? "Si" : "No")}\n");

        // Ejercicio 3: ¿Existe la carrera "DAW" entre los estudiantes?
        Console.WriteLine("Ejercicio 3: ¿Existe algún estudiante en la carrera DAW?");
        var carreras = estudiantes.Select(e => e.Carrera).Distinct().ToList();
        bool existeDAW = carreras.Contains("DAW");
        Console.WriteLine($"Respuesta: {(existeDAW ? "Si" : "No")}\n");

        // Ejercicio 4: ¿Existe un producto con ID 10?
        Console.WriteLine("Ejercicio 4: ¿Existe un producto con ID 10?");
        var idsProductos = productos.Select(p => p.Id).ToList();
        bool existeId10 = idsProductos.Contains(10);
        Console.WriteLine($"Respuesta: {(existeId10 ? "Si" : "No")}\n");
    }
}
