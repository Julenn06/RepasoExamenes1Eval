import {
  ChangeDetectorRef,
  Component
} from '@angular/core';
import {
  FormsModule,
  ReactiveFormsModule
} from '@angular/forms';
import {
  AnimalesRemoteService
} from '../services/animales.remote';
import {
  Animal
} from '../animal/animal';
import {
  Router
} from '@angular/router';

@Component({
  selector: 'app-animales',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './animales.html',
  styleUrl: './animales.css',
})
export class Animales {

  nombre: string = "";
  tipo: string = "";

  nombreNuevo: string = "";
  tipoNuevo: string = "";

  idAnimal: number = 0;
  nombreAnimal: string = "";

  todosLosAnimales: Animal[] = [];

  constructor(private cdr: ChangeDetectorRef, private remote: AnimalesRemoteService, private router: Router) {}

  anadirAnimal() {

    if (!this.nombre.trim()) {
      console.error('El nombre es obligatorio');
      return;
    }

    this.remote.createAnimal(this.nombre.trim(), this.tipo.trim()).subscribe({
      next: (animalCreado) => {
        console.log('Animal creado exitosamente:', animalCreado);
        this.verAnimales();
        this.limpiarDatos();
      },
      error: (err) => console.error('Error creando animal', err)
    });
  }

  verAnimales() {
    this.remote.getAllAnimales().subscribe({
      next: (hs) => {
        this.todosLosAnimales = hs;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error cargando Animales', err);
      }
    });
  }

  verAnimalPorId() {
    if (!this.idAnimal || this.idAnimal <= 0) {
      console.error('ID inválido');
      return;
    }

    this.remote.getAnimalById(this.idAnimal).subscribe({
      next: (animal) => {
        this.todosLosAnimales = [animal];
        console.log('Animal encontrado:', animal);
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error cargando Animal por ID', err);
        this.todosLosAnimales = [];
        this.cdr.markForCheck();
      }
    });
  }

  verAnimalPorNombre() {
    if (!this.nombreAnimal || this.nombreAnimal == null) {
      console.error('Nombre inválido');
      return;
    }

    this.remote.getAnimalByName(this.nombreAnimal).subscribe({
      next: (animal) => {
        this.todosLosAnimales = [animal];
        console.log('Animal encontrado:', animal);
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error cargando Animal por ID', err);
        this.todosLosAnimales = [];
        this.cdr.markForCheck();
      }
    });
  }

  nuevosDatos() {

    if (!this.idAnimal || this.idAnimal <= 0) {
      console.error('ID inválido para editar');
      return;
    }


    if (!this.nombreNuevo.trim() && !this.tipoNuevo.trim()) {
      console.error('Debe proporcionar al menos un campo para actualizar');
      return;
    }


    const changes: Partial < Animal > = {};
    if (this.nombreNuevo.trim()) changes.nombre = this.nombreNuevo.trim();
    if (this.tipoNuevo.trim()) changes.tipo = this.tipoNuevo.trim();

    this.remote.updateAnimal(this.idAnimal, changes).subscribe({
      next: (animalActualizado) => {
        console.log('Animal actualizado:', animalActualizado);
        this.verAnimales();
        this.limpiarDatosEdicion();
      },
      error: (err) => console.error('Error actualizando animal', err)
    });
  }

  eliminarAnimal() {

    if (!this.idAnimal || this.idAnimal <= 0) {
      console.error('ID inválido para eliminar');
      return;
    }


    if (!confirm(`Esta seguro de eliminar el animal con ID ${this.idAnimal}?`)) {
      return;
    }

    this.remote.deleteAnimal(this.idAnimal).subscribe({
      next: () => {
        console.log(`Animal con ID ${this.idAnimal} eliminado`);
        this.verAnimales();
        this.idAnimal = 0;
      },
      error: (err) => console.error('Error eliminando animal', err)
    });
  }

  eliminarAnimalPorNombre() {

    if (!this.nombreAnimal || this.nombreAnimal.trim() === '') {
      console.error('Nombre inválido para eliminar');
      return;
    }

    if (!confirm(`Esta seguro de eliminar el animal con Nombre ${this.nombreAnimal}?`)) {
      return;
    }

    this.remote.deleteAnimalByName(this.nombreAnimal).subscribe({
      next: () => {
        console.log(`Animal con Nombre ${this.nombreAnimal} eliminado`);
        this.verAnimales();
        this.nombreAnimal = "";
      },
      error: (err) => console.error('Error eliminando animal', err)
    });
  }

  unPar() {
    this.router.navigate(['/unPar']);
  }

  limpiarDatos() {
    this.nombre = "";
    this.tipo = "";
  }

  limpiarDatosEdicion() {
    this.nombreNuevo = "";
    this.tipoNuevo = "";
  }
}
