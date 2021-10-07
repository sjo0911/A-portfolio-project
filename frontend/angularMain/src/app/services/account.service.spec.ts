import { User } from './../models/user';
import { environment } from './../../environments/environment';
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { AccountService } from './account.service';

describe('AccountService', () => {
  let service: AccountService;
  let ROOT_URL = environment.ROOT_URL;
  let httpMock : HttpTestingController;
  let userMockup = {
    id: '1',
    email: "email",
    firstName: "Firstname",
    lastName: "Lastname",
    username: "Username",
    password: "Password",
    scopes: new Array(),
    loginDates: new Array()
  } as User;
  let id = "1";

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AccountService]
    });
    service = TestBed.inject(AccountService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Create: ', () => {
    it('Accountservice should be created', () => {
      expect(service).toBeTruthy();
    });
  });

  describe('API via GET', () => {
    it('should retrive specified user with getById(id)', () =>
    {
      service.getById(id).subscribe(user => expect(user).toEqual(userMockup))
      const request = httpMock.expectOne(`${ROOT_URL}/api/users/${id}`);
      expect(request.request.method).toBe('GET');
      request.flush(userMockup);
    })
    it('should retrive all user with getAll()', () =>
    {
      service.getAll().subscribe(users => expect(users).toEqual([userMockup]));
      const request = httpMock.expectOne(`${ROOT_URL}/api/users`);
      expect(request.request.method).toBe('GET');
      request.flush([userMockup]);
    })
  });

  describe('API via PUT', () => {
    it('should update user with update(id, userToUpdate)', () =>
    {
      service.update(userMockup.id, userMockup).subscribe((user) => expect(user).toEqual(userMockup));
      const request = httpMock.expectOne(`${ROOT_URL}/api/users/${userMockup.id}`);
      expect(request.request.method).toBe('PUT');
      request.flush(userMockup);
    })
    it('should add user to usersubject after update is completed', () =>
    {
      service.update(userMockup.id, userMockup).subscribe((user) => expect(user).toEqual(userMockup));
      const request = httpMock.expectOne(`${ROOT_URL}/api/users/${userMockup.id}`);
      request.flush(userMockup);
      expect(service.userValue).toEqual(userMockup);
    })
    it('should change password on user with changePass(id, userToChangePass)', () =>
    {
      service.changePass(userMockup.id, userMockup).subscribe((user) => expect(user).toEqual(userMockup));
      const request = httpMock.expectOne(`${ROOT_URL}/api/users/${userMockup.id}`);
      expect(request.request.method).toBe('PUT');
      request.flush(userMockup);
      expect(service.userValue).toEqual(userMockup);
    })
  });

  describe('API via DELETE', () => {
    it('should delete user with delete(id)', () =>
    {
      service.delete(userMockup.id).subscribe((user) => expect(user).toEqual(userMockup));
      const request = httpMock.expectOne(`${ROOT_URL}/api/users/changepass/${userMockup.id}`);
      expect(request.request.method).toBe('DELETE');
      request.flush(userMockup);
    })
  });

  describe('API via POST', () => {
    it('should register user with register(userToRegister)', () =>
    {
      service.register(userMockup).subscribe((user) => expect(user).toEqual(userMockup));
      const request = httpMock.expectOne(`${ROOT_URL}/api/users`);
      expect(request.request.method).toBe('POST');
      request.flush(userMockup);
    })
    it('should add user to usersubject after register is completed', () =>
    {
      service.register(userMockup).subscribe();
      const request = httpMock.expectOne(`${ROOT_URL}/api/users`);
      request.flush(userMockup);
      expect(service.userValue).toEqual(userMockup);
    })

  });



});
