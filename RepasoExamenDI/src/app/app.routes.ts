import { Routes } from '@angular/router';
import {Login} from './pages/login/login';
import { Register } from './pages/register/register';
import { FirstView } from './pages/first-view/first-view';
import { Prueba } from './pages/prueba/prueba';
import { LeerServer } from './pages/leer-server/leer-server';

export const routes: Routes = [
    {path: '', component: FirstView},
    {path: 'login', component: Login},
    {path: 'register', component: Register},
    {path: 'prueba', component: Prueba},
      {path: 'leerServer', component: LeerServer}
];
