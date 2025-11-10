using Repaso01.Models;

namespace Repaso01.Ejercicios;

public static class EjerciciosOrderBy
{
    public static void Ejecutar(List<Producto> productos, List<Empleado> empleados)
    {
        Console.WriteLine("\n═══════════════════════════════════════════════════════");
        Console.WriteLine("EJERCICIOS: ORDERBY() / ORDERBYDESCENDING()");
        Console.WriteLine("═══════════════════════════════════════════════════════\n");

        // Ejercicio 1: Ordenar productos por precio (menor a mayor)
        Console.WriteLine("Ejercicio 1: Productos ordenados por precio (ascendente):");
        var productosOrdenados = productos.OrderBy(p => p.Precio).ToList();
        foreach (var prod in productosOrdenados)
        {
            Console.WriteLine($"  • {prod}");
        }
        Console.WriteLine();

        // Ejercicio 2: Ordenar empleados por salario (mayor a menor)
        Console.WriteLine("Ejercicio 2: Empleados ordenados por salario (descendente):");
        var empleadosOrdenados = empleados.OrderByDescending(e => e.Salario).ToList();
        foreach (var emp in empleadosOrdenados)
        {
            Console.WriteLine($"  • {emp}");
        }
        Console.WriteLine();

        // Ejercicio 3: Ordenar productos por stock (menor a mayor)
        Console.WriteLine("Ejercicio 3: Productos ordenados por stock (ascendente):");
        var productosStock = productos.OrderBy(p => p.Stock).ToList();
        foreach (var prod in productosStock)
        {
            Console.WriteLine($"  • {prod}");
        }
        Console.WriteLine();

        // Ejercicio 4: Ordenar empleados por años de experiencia (mayor a menor)
        Console.WriteLine("Ejercicio 4: Empleados por experiencia (descendente):");
        var empleadosExperiencia = empleados.OrderByDescending(e => e.AñosExperiencia).ToList();
        foreach (var emp in empleadosExperiencia)
        {
            Console.WriteLine($"  • {emp}");
        }
        Console.WriteLine();
    }
}
