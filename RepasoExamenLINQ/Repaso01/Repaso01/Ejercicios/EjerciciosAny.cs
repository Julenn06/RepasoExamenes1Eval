using Repaso01.Models;

namespace Repaso01.Ejercicios;

public static class EjerciciosAny
{
    public static void Ejecutar(List<Estudiante> estudiantes, List<Producto> productos)
    {
        Console.WriteLine("\n═══════════════════════════════════════════════════════");
        Console.WriteLine("EJERCICIOS: ANY()");
        Console.WriteLine("═══════════════════════════════════════════════════════\n");

        // Ejercicio 1: ¿Hay algún estudiante con nota perfecta (10)?
        Console.WriteLine("Ejercicio 1: ¿Hay algún estudiante con nota media de 10?");
        bool hayNotaPerfecta = estudiantes.Any(e => e.NotaMedia == 10);
        Console.WriteLine($"Respuesta: {(hayNotaPerfecta ? "Si" : "No")}\n");

        // Ejercicio 2: ¿Hay algún producto sin stock de la categoría Electrónica?
        Console.WriteLine("Ejercicio 2: ¿Hay productos de Electrónica sin stock?");
        bool haySinStock = productos.Any(p => p.Categoria == "Electrónica" && p.Stock == 0);
        Console.WriteLine($"Respuesta: {(haySinStock ? "Si" : "No")}\n");

        // Ejercicio 3: ¿Hay algún estudiante menor de 18 años en la carrera ASIR?
        Console.WriteLine("Ejercicio 3: ¿Hay estudiantes menores de edad en ASIR?");
        bool hayMenoresASIR = estudiantes.Any(e => e.Carrera == "ASIR" && e.Edad < 18);
        Console.WriteLine($"Respuesta: {(hayMenoresASIR ? "Si" : "No")}\n");

        // Ejercicio 4: ¿Existe algún producto con precio mayor a $500?
        Console.WriteLine("Ejercicio 4: ¿Hay productos con precio > $500?");
        bool hayCaros = productos.Any(p => p.Precio > 500);
        Console.WriteLine($"Respuesta: {(hayCaros ? "Si" : "No")}\n");
    }
}
