import { Injectable } from '@angular/core';
import {HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';

@Injectable()
export class JwtInterceptor implements HttpInterceptor{
    //Get jwt token from out session, set an 
   // Authorization header so the server can validate and authenticate user requests
    intercept(req: HttpRequest<any>, next: HttpHandler){
        const token = sessionStorage.getItem('token');
        //if there is a token..
        if(token){
            //clone method creates copy of http request, 
            //adds authentication header to that copy
            //then sends it using next.handle method
            const authReq = req.clone({
                headers: req.headers.set('Authorization', `Bearer ${token}`)
            });
            return next.handle(authReq);
        }
        //if there is no token send original request
        return next.handle(req);
    }
}