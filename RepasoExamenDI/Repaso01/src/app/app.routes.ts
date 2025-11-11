import { Routes } from '@angular/router';
import { FirstView } from './pages/first-view/first-view';
import { Animales } from './pages/animales/animales';

export const routes: Routes = [
    {path: '', component: FirstView},
    {path: 'animales', component: Animales}
];
