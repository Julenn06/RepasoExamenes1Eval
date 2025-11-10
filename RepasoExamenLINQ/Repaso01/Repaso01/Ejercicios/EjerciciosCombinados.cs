using Repaso01.Models;

namespace Repaso01.Ejercicios;

public static class EjerciciosCombinados
{
    public static void Ejecutar(List<Estudiante> estudiantes, List<Producto> productos)
    {
        Console.WriteLine("\n═══════════════════════════════════════════════════════");
        Console.WriteLine("EJERCICIOS COMBINADOS (BONUS)");
        Console.WriteLine("═══════════════════════════════════════════════════════\n");

        // Ejercicio 1: Top 3 estudiantes con mejor nota
        Console.WriteLine("Ejercicio 1: Top 3 estudiantes con mejor nota:");
        var top3Estudiantes = estudiantes
            .OrderByDescending(e => e.NotaMedia)
            .Take(3)
            .ToList();
        int posicion = 1;
        foreach (var est in top3Estudiantes)
        {
            Console.WriteLine($"  {posicion++}. {est}");
        }
        Console.WriteLine();

        // Ejercicio 2: Productos de Electrónica con stock, ordenados por precio
        Console.WriteLine("Ejercicio 2: Electrónica disponible (ordenado por precio):");
        var electronicaDisponible = productos
            .Where(p => p.Categoria == "Electrónica" && p.Stock > 0)
            .OrderBy(p => p.Precio)
            .Select(p => $"{p.Nombre} - ${p.Precio} ({p.Stock} unidades)")
            .ToList();
        foreach (var prod in electronicaDisponible)
        {
            Console.WriteLine($"  • {prod}");
        }
        Console.WriteLine();

        // Ejercicio 3: Estudiantes aprobados de DAM ordenados por nota descendente
        Console.WriteLine("Ejercicio 3: Ranking de aprobados en DAM:");
        var rankingDAM = estudiantes
            .Where(e => e.Carrera == "DAM" && e.Aprobado)
            .OrderByDescending(e => e.NotaMedia)
            .Select((e, index) => $"{index + 1}. {e.Nombre} - {e.NotaMedia}")
            .ToList();
        foreach (var item in rankingDAM)
        {
            Console.WriteLine($"  {item}");
        }
        Console.WriteLine();

        // Ejercicio 4: Productos baratos (< $100) con stock, contando cantidad
        Console.WriteLine("Ejercicio 4: Productos económicos disponibles:");
        var productosBaratos = productos
            .Where(p => p.Precio < 100 && p.Stock > 0)
            .OrderBy(p => p.Precio)
            .ToList();
        Console.WriteLine($"  Total de productos: {productosBaratos.Count()}");
        Console.WriteLine($"  Stock combinado: {productosBaratos.Sum(p => p.Stock)} unidades");
        Console.WriteLine($"  Valor promedio: ${productosBaratos.Average(p => p.Precio):F2}");
        Console.WriteLine("  Productos:");
        foreach (var prod in productosBaratos)
        {
            Console.WriteLine($"    • {prod}");
        }
        Console.WriteLine();
    }
}
