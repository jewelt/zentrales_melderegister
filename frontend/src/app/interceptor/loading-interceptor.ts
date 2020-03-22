import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {LoadingService} from '../services/loading.service';

@Injectable({
  providedIn: 'root'
})
export class LoadingInterceptor implements HttpInterceptor {


  constructor(private loadingService: LoadingService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    this.loadingService.addPending();
    return next.handle(req).pipe(
      tap((result) => {
        if (result.type !== 0) {
          this.loadingService.removePending();
        }
      }, () => {
        this.loadingService.removePending();
      }),
    );
  }
}
