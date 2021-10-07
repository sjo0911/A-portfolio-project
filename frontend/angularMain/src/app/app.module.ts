import { RegisterPageComponent } from './pages/userpages/register-page/register-page.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatDialogModule} from "@angular/material/dialog";
import { MatSliderModule } from '@angular/material/slider';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import { SideNavComponent } from './pages/mainpages/side-nav/side-nav.component';
import { MainPageComponent } from './pages/mainpages/main-page/main-page.component';
import {MatButtonModule} from '@angular/material/button';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {MatExpansionModule} from '@angular/material/expansion';
import { PostTextComponent } from './components/postcomponents/post-text/post-text.component';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import { ShowPostsComponent } from './components/postcomponents/show-posts/show-posts.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import { ShowSinglePostComponent } from './components/postcomponents/show-single-post/show-single-post.component';
import {MatCardModule} from '@angular/material/card';
import { GuestbookPageComponent } from './pages/guestbook/guestbook-page/guestbook-page.component';
import { LoginPageComponent } from './pages/userpages/login-page/login-page.component';
import { UserPageComponent } from './pages/userpages/user-page/user-page.component';
import { JavagamesComponent } from './pages/javagames/javagames.component';




@NgModule({
  declarations: [
    AppComponent,
    SideNavComponent,
    MainPageComponent,
    LoginPageComponent,
    UserPageComponent,
    PostTextComponent,
    ShowPostsComponent,
    ShowSinglePostComponent,
    GuestbookPageComponent,
    RegisterPageComponent,
    JavagamesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatSliderModule,
    MatSidenavModule,
    MatIconModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatInputModule,
    MatPaginatorModule,
    MatCardModule,

  ],
  exports: [

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
