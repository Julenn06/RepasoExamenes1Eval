using Repaso01.Models;

namespace Repaso01.Ejercicios;

public static class EjerciciosSum
{
    public static void Ejecutar(List<Producto> productos, List<Empleado> empleados, List<Estudiante> estudiantes)
    {
        Console.WriteLine("\n═══════════════════════════════════════════════════════");
        Console.WriteLine("EJERCICIOS: SUM()");
        Console.WriteLine("═══════════════════════════════════════════════════════\n");

        // Ejercicio 1: Suma total de precios de productos en oferta
        Console.WriteLine("Ejercicio 1: Valor total de productos en oferta:");
        decimal totalOferta = productos.Where(p => p.EnOferta).Sum(p => p.Precio);
        Console.WriteLine($"  Total: ${totalOferta}\n");

        // Ejercicio 2: Total de años de experiencia en IT
        Console.WriteLine("Ejercicio 2: Total años de experiencia en IT:");
        int totalExpIT = empleados.Where(e => e.Departamento == "IT").Sum(e => e.AñosExperiencia);
        Console.WriteLine($"  Total: {totalExpIT} años\n");

        // Ejercicio 3: Suma total de stock de todos los productos
        Console.WriteLine("Ejercicio 3: Stock total de todos los productos:");
        int stockTotal = productos.Sum(p => p.Stock);
        Console.WriteLine($"  Total: {stockTotal} unidades\n");

        // Ejercicio 4: Suma de edades de estudiantes aprobados
        Console.WriteLine("Ejercicio 4: Suma de edades de estudiantes aprobados:");
        int sumaEdadesAprobados = estudiantes.Where(e => e.Aprobado).Sum(e => e.Edad);
        Console.WriteLine($"  Total: {sumaEdadesAprobados} años\n");
    }
}
