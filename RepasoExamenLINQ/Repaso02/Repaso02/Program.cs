namespace Repaso02
{
    class Program
    {
        static void Main(string[] args)
        {
            // =============================
            // ===== EJERCICIOS LINQ =======
            // =============================

            // Ejercicio 1
            Console.WriteLine("Ejercicio 1: Quiero saber si hay algún alumno que tenga más de 9 de nota.");
            var notas1 = new List<int> { 5, 7, 8, 9, 10, 6, 4 };
            var mayorA9 = notas1.Any(x => x > 9);
            Console.WriteLine("Hay alguno o no?: " + mayorA9);

            // Ejercicio 2
            Console.WriteLine("\nEjercicio 2: Necesito una lista con solo los nombres que empiecen por la letra 'A'.");
            var nombres2 = new List<string> { "Ana", "Luis", "Andrea", "Pedro", "Alberto", "Marta" };
            // Alternativa LINQ (más corta): 
            var nombresConA = nombres2.Where(n => n.StartsWith("A")).ToList();
            foreach (var n in nombresConA)
            {
                Console.WriteLine("Hay alguno con A: " + n);
            }

            // Ejercicio 3
            Console.WriteLine("\nEjercicio 3: Quiero ordenar los precios de menor a mayor.");
            var precios3 = new List<double> { 9.99, 1.99, 5.49, 3.20, 12.99 };
            var preciosOrdenados = precios3.OrderBy(x => x).ToList();
            Console.WriteLine(string.Join(" ", preciosOrdenados));

            // Ejercicio 4
            Console.WriteLine("\nEjercicio 4: ¿Todos los productos cuestan más de 1 euro?");
            var precios4 = new List<double> { 9.99, 1.99, 5.49, 3.20, 12.99 };
            var todosMasDe1 = precios4.All(p => p > 1.00);
            Console.WriteLine("Todos mayor a 1?: " + todosMasDe1);

            // Ejercicio 5 (CORREGIDO)
            Console.WriteLine("\nEjercicio 5: Dame el primer número par de la lista, o cero si no hay.");
            var numeros5 = new List<int> { 1, 3, 5, 8, 9, 11 };
            // Usamos FirstOrDefault para obtener el primer par o 0 si no existe
            var numeroPar = numeros5.FirstOrDefault(x => x % 2 == 0);
            Console.WriteLine("El primer numero par: " + numeroPar);

            // Ejercicio 6 (CORREGIDO)
            Console.WriteLine("\nEjercicio 6: Necesito saber cuántos nombres tienen más de 5 letras.");
            var nombres6 = new List<string> { "Carlos", "Ana", "Beatriz", "Luis", "Fernando", "Marta" };
            // Usamos Count con predicado
            var cuantosMasDe5 = nombres6.Count(x => x.Length > 5);
            Console.WriteLine("Tienen más de 5 letras: " + cuantosMasDe5);

            // Ejercicio 7
            Console.WriteLine("\nEjercicio 7: ¿La lista contiene el número 7?");
            var numeros7 = new int[] { 2, 4, 6, 7, 8, 10 };
            var estaEl7 = numeros7.Contains(7);
            Console.WriteLine("Esta el 7?: " + estaEl7);

            // Ejercicio 8
            Console.WriteLine("\nEjercicio 8: Dame el salario más alto y el más bajo.");
            var salarios8 = new List<decimal> { 1200m, 2500m, 1800m, 3100m, 900m };
            // Podemos usar Min y Max directamente
            var salarioMin = salarios8.Min();
            var salarioMax = salarios8.Max();
            Console.WriteLine("Salario mas pequeño: " + salarioMin);
            Console.WriteLine("Salario mas grande: " + salarioMax);

            // Ejercicio 9
            Console.WriteLine("\nEjercicio 9: Quiero la nota media de la clase.");
            var notas9 = new List<int> { 4, 7, 9, 6, 8, 10, 5 };
            var media = notas9.Average();
            Console.WriteLine("Media de las notas: " + media);

            // Ejercicio 10
            Console.WriteLine("\nEjercicio 10: Pásame la lista de nombres en un array, ordenados alfabéticamente de Z a A.");
            var nombres10 = new List<string> { "Carlos", "Ana", "Beatriz", "Luis", "Fernando", "Marta" };
            var ordenPorZ = nombres10.OrderByDescending(x => x).ToArray();
            Console.WriteLine("Orden de la Z a la A:");
            foreach (var n in ordenPorZ)
            {
                Console.WriteLine(n);
            }

            Console.WriteLine("\n=================================");
            Console.WriteLine("   Fin de los ejercicios LINQ");
            Console.WriteLine("=================================");
        }
    }
}
