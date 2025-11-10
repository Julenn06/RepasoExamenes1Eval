using Repaso01.Models;

namespace Repaso01.Ejercicios;

public static class EjerciciosSelect
{
    public static void Ejecutar(List<Estudiante> estudiantes, List<Producto> productos)
    {
        Console.WriteLine("\n═══════════════════════════════════════════════════════");
        Console.WriteLine("EJERCICIOS: SELECT()");
        Console.WriteLine("═══════════════════════════════════════════════════════\n");

        // Ejercicio 1: Obtener solo los nombres de estudiantes aprobados
        Console.WriteLine("Ejercicio 1: Nombres de estudiantes aprobados:");
        var nombresAprobados = estudiantes.Where(e => e.Aprobado).Select(e => e.Nombre).ToList();
        Console.WriteLine($"  {string.Join(", ", nombresAprobados)}\n");

        // Ejercicio 2: Crear lista de productos con descuento del 20%
        Console.WriteLine("Ejercicio 2: Productos con 20% de descuento:");
        var productosConDescuento = productos.Select(p => new
        {
            p.Nombre,
            PrecioOriginal = p.Precio,
            PrecioDescuento = p.Precio * 0.8m
        }).ToList();
        foreach (var prod in productosConDescuento)
        {
            Console.WriteLine($"  • {prod.Nombre}: ${prod.PrecioOriginal} → ${prod.PrecioDescuento:F2}");
        }
        Console.WriteLine();

        // Ejercicio 3: Obtener solo las edades de estudiantes de ASIR
        Console.WriteLine("Ejercicio 3: Edades de estudiantes de ASIR:");
        var edadesASIR = estudiantes.Where(e => e.Carrera == "ASIR").Select(e => e.Edad).ToList();
        Console.WriteLine($"  {string.Join(", ", edadesASIR)} años\n");

        // Ejercicio 4: Crear lista con nombre y categoría de productos en oferta
        Console.WriteLine("Ejercicio 4: Resumen de productos en oferta:");
        var resumenOfertas = productos.Where(p => p.EnOferta).Select(p => new
        {
            Producto = p.Nombre,
            Categoria = p.Categoria,
            Ahorro = p.Precio * 0.3m
        }).ToList();
        foreach (var item in resumenOfertas)
        {
            Console.WriteLine($"  • {item.Producto} ({item.Categoria}) - Ahorra: ${item.Ahorro:F2}");
        }
        Console.WriteLine();
    }
}
