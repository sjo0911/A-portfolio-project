import { RegisterPageComponent } from './pages/userpages/register-page/register-page.component';
import { GuestbookPageComponent } from './pages/guestbook/guestbook-page/guestbook-page.component';
import { MainPageComponent } from './pages/mainpages/main-page/main-page.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './helpers/authguard';
import { LoginPageComponent } from './pages/userpages/login-page/login-page.component';
import { UserPageComponent } from './pages/userpages/user-page/user-page.component';

const routes: Routes = [
  { path:  '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: MainPageComponent, outlet: "primary" },
  { path: 'Login', component: LoginPageComponent, outlet: 'userSidebar' },
  { path: 'Guestbook', component: GuestbookPageComponent, outlet: "primary"},
  { path: 'UserPage', component: UserPageComponent, outlet: 'userSidebar', canActivate: [AuthGuard]},
  {path: 'Login/Register', component: RegisterPageComponent, outlet: 'userSidebar'}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
