import { environment } from './../../environments/environment.prod';
import { AccountService } from './../services/account.service';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";


@Injectable()
export class JwtInterceptor implements HttpInterceptor {
  constructor(private accountService : AccountService) {
  }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const user = this.accountService.userValue;
    const isLoggedIn = user && user.token;
    const isApiUrl = req.url.startsWith(environment.ROOT_URL);
    if(isLoggedIn && isApiUrl) {
      req = req.clone({
        setHeaders : {
          Authorization: `Bearer ${user?.token}`
        }
      });
    }

    return next.handle(req);
  }

}
