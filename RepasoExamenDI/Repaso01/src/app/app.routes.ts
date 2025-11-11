import { Routes } from '@angular/router';
import { FirstView } from './pages/first-view/first-view';
import { Animales } from './pages/animales/animales';
import { UnPar } from './pages/un-par/un-par';
import { Animalito } from './pages/animalito/animalito';

export const routes: Routes = [
  { path: '', component: FirstView },
  { path: 'animales', component: Animales },
  { path: 'unPar', component: UnPar },
  { path: 'animalito/:id', component: Animalito }
];
