using System;
using System.Collections.Generic;
using System.Linq;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace EjerciciosLINQ_Tanda2
{
    class Program
    {
        // ===== CLASES DE APOYO =====
        public class Alumno
        {
            public string? Nombre { get; set; }
            public int Edad { get; set; }
            public double Nota { get; set; }
        }

        public class Producto
        {
            public string? Nombre { get; set; }
            public decimal Precio { get; set; }
            public string? Categoria { get; set; }
        }

        public class Empleado
        {
            public string? Nombre { get; set; }
            public int Edad { get; set; }
            public decimal Salario { get; set; }
        }

        static void Main(string[] args)
        {
            // ===== DATOS BASE =====
            var numeros = new List<int> { -3, 0, 4, 7, 12, 25, 100, 101 };
            var nombres = new List<string> { "Ana", "Beatriz", "Carlos", "alberto", "Marta", "David", "Lucía" };
            var temperaturas = new List<double> { 18.5, 20.1, 22.3, 19.8, 21.5, 23.2, 17.9 };
            var ventas = new List<decimal> { 150.5m, 300.2m, 100.75m, 90m, 250m };
            var correos = new List<string> { "ana@gmail.com", "carlos@hotmail.com", "marta@yahoo.com" };

            var alumnos = new List<Alumno>
            {
                new Alumno { Nombre = "Lucía", Edad = 20, Nota = 8.5 },
                new Alumno { Nombre = "Mario", Edad = 22, Nota = 5.9 },
                new Alumno { Nombre = "Laura", Edad = 19, Nota = 9.1 },
                new Alumno { Nombre = "Sergio", Edad = 21, Nota = 4.5 },
            };

            var productos = new List<Producto>
            {
                new Producto { Nombre = "Ratón", Precio = 25.99m, Categoria = "Electrónica" },
                new Producto { Nombre = "Teclado", Precio = 49.99m, Categoria = "Electrónica" },
                new Producto { Nombre = "Camiseta", Precio = 15.50m, Categoria = "Ropa" },
                new Producto { Nombre = "Portátil", Precio = 799.99m, Categoria = "Electrónica" },
                new Producto { Nombre = "Zapatillas", Precio = 60.00m, Categoria = "Ropa" },
            };

            var empleados = new List<Empleado>
            {
                new Empleado { Nombre = "Javier", Edad = 25, Salario = 1200m },
                new Empleado { Nombre = "Patricia", Edad = 35, Salario = 2200m },
                new Empleado { Nombre = "Sandra", Edad = 40, Salario = 3100m },
                new Empleado { Nombre = "Andrés", Edad = 29, Salario = 1850m },
            };

            // ===== EJERCICIOS =====

            Console.WriteLine("Ejercicio 1: Comprueba si existe algún número negativo en la lista.");
            var numeroNegativo = numeros.Any(x => x < 0);
            Console.WriteLine($"{numeroNegativo}");

            Console.WriteLine("\nEjercicio 2: Verifica si todos los nombres empiezan con mayúscula.");
            var empiezanConMayuscula = nombres.All(x => char.IsUpper(x[0]));
            Console.WriteLine($"{empiezanConMayuscula}");

            Console.WriteLine("\nEjercicio 3: Obtén solo los números pares de la lista.");
            var numerosPares = numeros.Where(x => x % 2 == 0);

            Console.WriteLine("Numeros pares: ");
            foreach (var numero in numerosPares) {
                Console.WriteLine(numero);
            }

            Console.WriteLine("\nEjercicio 4: Calcula cuántos productos cuestan más de 50€.");
            var masDe50 = ventas.Count(x => x > 50);
            Console.WriteLine("Cuantos productos cuestan mas de 50: " + masDe50);

            Console.WriteLine("\nEjercicio 5: Comprueba si la lista de números contiene el número 7.");
            var tieneEl7 = numeros.Contains(7);
            Console.WriteLine("Esta el numero 7 en la lista?: " + tieneEl7);

            Console.WriteLine("\nEjercicio 6: Devuelve el primer número mayor que 100 o 0 si no hay ninguno.");
            var numeroMayorA100 = numeros.FirstOrDefault(x => x > 100);
            Console.WriteLine("Primer numero mayor a 100: " + numeroMayorA100);

            Console.WriteLine("\nEjercicio 7: Ordena los nombres alfabéticamente y muéstralos en pantalla.");
            var ordenNombres = alumnos.OrderBy(x => x.Nombre).Select(x => x.Nombre).ToList();
            Console.WriteLine("Orden de nombres: ");
            for (var i = 0; i < ordenNombres.Count; i++) {
                Console.WriteLine(ordenNombres[i]);
            }

            Console.WriteLine("\nEjercicio 8: Muestra los salarios de los empleados del más alto al más bajo.");
            var salariosOrdenados = empleados.OrderByDescending(x => x.Salario).Select(x=> x.Salario).ToList();

            Console.WriteLine("Salarios Ordenados: ");
            for (var i = 0; i < salariosOrdenados.Count; i++) {
                Console.WriteLine(salariosOrdenados[i]);
            }

            Console.WriteLine("\nEjercicio 9: Muestra la nota más alta de todos los alumnos.");
            var notaMasAlta = alumnos.OrderByDescending(x => x.Nota).Select(x=> x.Nota).ToList();
            Console.WriteLine("Nota mas Alta: " + notaMasAlta[0]);


            Console.WriteLine("\nEjercicio 10: Muestra la nota más baja de todos los alumnos.");
            var notaMasBaja = alumnos.OrderBy(x => x.Nota).Select(x => x.Nota).ToList();
            Console.WriteLine("Nota mas Baja: " + notaMasBaja[0]);

            Console.WriteLine("\nEjercicio 11: Calcula la temperatura media de la semana.");
            var temperaturaMedia = temperaturas.Average();
            double redondeado = Math.Round(temperaturaMedia, 2);
            Console.WriteLine("Temperatura Media: " + redondeado);


            Console.WriteLine("\nEjercicio 12: Suma el total de todas las ventas registradas.");
            var total = ventas.Sum();
            Console.WriteLine("Suma de todas las ventas: " + total);


            Console.WriteLine("\nEjercicio 13: Obtén una lista con solo los nombres de los alumnos.");
            var nombresAlumnos = alumnos.OrderBy(x => x.Nombre).Select(x => x.Nombre).ToList();

            Console.WriteLine("Lista de los alumnos: ");
            for (int i = 0; i < nombresAlumnos.Count; i++) {
                Console.WriteLine(nombresAlumnos[i]);
            }

            Console.WriteLine("\nEjercicio 14: Convierte la lista de números en una nueva lista.");
            var nuevaLista = numeros.OrderByDescending(x => x).ToList();

            Console.WriteLine("Nueva lista de numeros");
            for (int i = 0; i < nuevaLista.Count(); i++) { 
            Console.WriteLine(nuevaLista[i]);
            }


            Console.WriteLine("\nEjercicio 15: Convierte la lista de nombres a un array.");
            var arrayNombres = nombres.ToArray();
            Console.WriteLine("Nuevo Array: ");
            for (int i = 0; i < arrayNombres.Length; i++) {
                Console.WriteLine(arrayNombres[i]);
            }

            Console.WriteLine("\nEjercicio 16: Comprueba si existe algún producto con un precio superior a 1000€.");
            var productoCaro = productos.Any(x => x.Precio > 1000);
            Console.WriteLine("¿Hay algún producto de más de 1000€?: " + productoCaro);

            Console.WriteLine("\nEjercicio 17: Verifica si todos los números son positivos.");
            var todosPositivos = numeros.All(x => x > 0);
            Console.WriteLine("¿Todos los números son positivos?: " + todosPositivos);

            Console.WriteLine("\nEjercicio 18: Muestra solo las palabras con más de 5 letras.");
            var palabrasLargas = nombres.Where(x => x.Length > 5).ToList();
            Console.WriteLine("Palabras con más de 5 letras:");
            foreach (var palabra in palabrasLargas)
            {
                Console.WriteLine(palabra);
            }

            Console.WriteLine("\nEjercicio 19: Calcula cuántos alumnos han aprobado.");
            var aprobados = alumnos.Count(x => x.Nota >= 5);
            Console.WriteLine("Cantidad de alumnos aprobados: " + aprobados);

            Console.WriteLine("\nEjercicio 20: Comprueba si hay algún correo que sea de Gmail.");
            var tieneGmail = correos.Any(x => x.Contains("@gmail.com"));
            Console.WriteLine("¿Hay algún correo de Gmail?: " + tieneGmail);

            Console.WriteLine("\nEjercicio 21: Muestra el primer alumno con una nota superior a 9 o ninguno si no existe.");
            var alumnoSobresaliente = alumnos.FirstOrDefault(x => x.Nota > 9);
            if (alumnoSobresaliente != null)
                Console.WriteLine("Primer alumno con nota > 9: " + alumnoSobresaliente.Nombre);
            else
                Console.WriteLine("Ningún alumno tiene nota mayor a 9.");

            Console.WriteLine("\nEjercicio 22: Ordena los precios de los productos de menor a mayor.");
            var preciosOrdenados = productos.OrderBy(x => x.Precio).Select(x => x.Precio).ToList();
            Console.WriteLine("Precios ordenados de menor a mayor:");
            foreach (var precio in preciosOrdenados)
            {
                Console.WriteLine(precio + "€");
            }

            Console.WriteLine("\nEjercicio 23: Ordena los empleados por su salario de mayor a menor.");
            var empleadosOrdenados = empleados.OrderByDescending(x => x.Salario).ToList();
            Console.WriteLine("Empleados ordenados por salario (desc):");
            foreach (var emp in empleadosOrdenados)
            {
                Console.WriteLine($"{emp.Nombre} - {emp.Salario}€");
            }

            Console.WriteLine("\nEjercicio 24: Muestra el salario máximo, el mínimo y el salario medio.");
            var salarioMax = empleados.Max(x => x.Salario);
            var salarioMin = empleados.Min(x => x.Salario);
            var salarioMedio = empleados.Average(x => (double)x.Salario);
            Console.WriteLine($"Salario máximo: {salarioMax}€ | Mínimo: {salarioMin}€ | Medio: {Math.Round(salarioMedio, 2)}€");

            Console.WriteLine("\nEjercicio 25: Calcula el salario total de todos los empleados.");
            var salarioTotal = empleados.Sum(x => x.Salario);
            Console.WriteLine("Suma total de los salarios: " + salarioTotal + "€");

            Console.WriteLine("\nEjercicio 26: Muestra una lista con las edades de todos los alumnos.");
            var edades = alumnos.Select(x => x.Edad).ToList();
            Console.WriteLine("Edades de los alumnos:");
            foreach (var edad in edades)
            {
                Console.WriteLine(edad);
            }

            Console.WriteLine("\nEjercicio 27: Convierte la lista de productos en un array y luego de nuevo en lista.");
            var arrayProductos = productos.ToArray();
            var listaDesdeArray = arrayProductos.ToList();
            Console.WriteLine("Productos convertidos en lista nuevamente:");
            foreach (var p in listaDesdeArray)
            {
                Console.WriteLine(p.Nombre + " - " + p.Precio + "€");
            }

            Console.WriteLine("\nEjercicio 28: Obtén los productos de la categoría 'Electrónica' con precio menor a 200€, ordénalos de mayor a menor precio y guárdalos en una lista.");
            var productosFiltrados = productos
                .Where(x => x.Categoria == "Electrónica" && x.Precio < 200)
                .OrderByDescending(x => x.Precio)
                .ToList();

            Console.WriteLine("Productos de electrónica menores a 200€ (ordenados desc):");
            foreach (var prod in productosFiltrados)
            {
                Console.WriteLine($"{prod.Nombre} - {prod.Precio}€");
            }


            Console.WriteLine("\n=================================");
            Console.WriteLine("   Fin de los ejercicios LINQ 2");
            Console.WriteLine("=================================");
        }
    }
}
