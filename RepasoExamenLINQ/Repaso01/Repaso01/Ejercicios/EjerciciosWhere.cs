using Repaso01.Models;

namespace Repaso01.Ejercicios;

public static class EjerciciosWhere
{
    public static void Ejecutar(List<Estudiante> estudiantes, List<Producto> productos)
    {
        Console.WriteLine("\n═══════════════════════════════════════════════════════");
        Console.WriteLine("EJERCICIOS: WHERE()");
        Console.WriteLine("═══════════════════════════════════════════════════════\n");

        // Ejercicio 1: Filtrar estudiantes aprobados de DAM
        Console.WriteLine("Ejercicio 1: Estudiantes aprobados de la carrera DAM:");
        var estudiantesDAM = estudiantes.Where(e => e.Aprobado && e.Carrera == "DAM").ToList();
        foreach (var est in estudiantesDAM)
        {
            Console.WriteLine($"  • {est}");
        }
        Console.WriteLine();

        // Ejercicio 2: Productos en oferta con precio menor a $50
        Console.WriteLine("Ejercicio 2: Productos en oferta con precio < $50:");
        var productosOferta = productos.Where(p => p.EnOferta && p.Precio < 50).ToList();
        foreach (var prod in productosOferta)
        {
            Console.WriteLine($"  • {prod}");
        }
        Console.WriteLine();

        // Ejercicio 3: Estudiantes suspendidos menores de 20 años
        Console.WriteLine("Ejercicio 3: Estudiantes suspendidos con edad < 20:");
        var estudiantesSuspendidosJovenes = estudiantes.Where(e => !e.Aprobado && e.Edad < 20).ToList();
        foreach (var est in estudiantesSuspendidosJovenes)
        {
            Console.WriteLine($"  • {est}");
        }
        Console.WriteLine();

        // Ejercicio 4: Productos de Muebles con stock disponible
        Console.WriteLine("Ejercicio 4: Muebles con stock disponible:");
        var mueblesDisponibles = productos.Where(p => p.Categoria == "Muebles" && p.Stock > 0).ToList();
        foreach (var prod in mueblesDisponibles)
        {
            Console.WriteLine($"  • {prod}");
        }
        Console.WriteLine();
    }
}
