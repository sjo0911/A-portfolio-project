import { WebReqService } from './web-req.service';
import { EMPTY, Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from '../models/user';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private userSubject : BehaviorSubject<User | null>;
  public user : Observable<User | null>;

  constructor(
    private webReqService : WebReqService
  ) {
    var storedUser = localStorage.getItem('user');
    if(storedUser == null){
      this.userSubject = new BehaviorSubject<User | null>(null);
    } else {
      this.userSubject = new BehaviorSubject<User | null>(JSON.parse(storedUser));
    }
    this.user = this.userSubject.asObservable();
  }

  public get userValue(): User | null {
    return this.userSubject.value;
  }

  login(email: string, password: string) {
    return this.webReqService.post("api/login/",
    {
      "email": email,
      "password": password
    }).pipe(map(user => {
      localStorage.setItem('user', JSON.stringify(user));
      this.userSubject.next(user as User);
      return user;
    }))
  }

  logout() {
    localStorage.removeItem('user');
    this.userSubject.next(null);
  }

  register(user : User) {
    return this.webReqService.post("api/users", user).pipe(map(regedUser => {
        localStorage.setItem('user', JSON.stringify(regedUser));
        this.userSubject.next(regedUser as User);
      return regedUser;
    }));
  }

  getAll() {
    return this.webReqService.get("api/users");
  }

  getById(id: string) {
    return this.webReqService.get(`api/users/${id}`);
  }

  update(id: string, userToUptade : User) {
    return this.webReqService.put(`api/users/${id}`, userToUptade)
      .pipe(map(x => {
        //Checks if current user is being updated and add changes to localstorage.
        if(id == this.userValue?.id) {
          const user = {...this.userValue, ...userToUptade};
          localStorage.setItem('user', JSON.stringify(user));
          this.userSubject.next(user);
        }
        return x;
      }));
  }

  changePass(id: string, userToUptade : User) {
    return this.webReqService.put(`api/users/changepass/${id}` , userToUptade);
  }

  delete(id: string){
    return this.webReqService.delete(`api/users/${id}`)
    .pipe(map(x => {
      //If current uses being deletet then logout
      if(id == this.userValue?.id){
        this.logout();
      }
      return x;
    }));
  }


}
