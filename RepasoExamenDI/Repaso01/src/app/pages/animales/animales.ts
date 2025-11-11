import {
  ChangeDetectorRef,
  Component
} from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AnimalesRemoteService } from '../services/animales.remote';
import { Animal } from '../animal/animal';

@Component({
  selector: 'app-animales',
  imports: [FormsModule,ReactiveFormsModule],
  templateUrl: './animales.html',
  styleUrl: './animales.css',
})

export class Animales {

  crearAnimalInput = true;
  nombre: string= "";
  tipo: string="";
  idAnimal: number=0;
  todosLosAnimales: Animal[] = [];

  constructor(private cdr: ChangeDetectorRef, private remote : AnimalesRemoteService){}

  crearAnimal() {
    this.crearAnimalInput = false;
  }

  anadirAnimal() {
    this.remote.createAnimal(this.nombre, this.tipo).subscribe({
      next: () => {
        this.verAnimales();
      },
      error: (err) => console.error('Error creando animal', err)
    });
    this.limpiarDatos();
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

  vetAnimalPorId() {
             this.remote.getAnimalById(this.idAnimal).subscribe({
      next: (hs) => {
        this.todosLosAnimales = [hs];
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error cargando Animales', err);
      }
    });
  }

  editarAnimal() {
    throw new Error('Method not implemented.');
  }

  eliminarAnimal() {
    throw new Error('Method not implemented.');
  }

  limpiarDatos() {
    this.crearAnimalInput = true;
    this.nombre = "";
    this.tipo = "";
  }
}
