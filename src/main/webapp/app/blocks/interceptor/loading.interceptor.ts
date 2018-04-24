import { Observable } from 'rxjs/Observable';
import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { SERVER_API_URL } from '../../app.constants';

import { NgxSpinnerService } from 'ngx-spinner';

export class LoadingInterceptor implements HttpInterceptor {

    constructor(private spinnerService: NgxSpinnerService) {
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (!request || !request.url || (/^http/.test(request.url) && !(SERVER_API_URL && request.url.startsWith(SERVER_API_URL)))) {
            return next.handle(request);
        }
        this.spinnerService.show();
        return next.handle(request).do((event: HttpEvent<any>) => {
            if (event instanceof HttpResponse) {
                if (!event.url.endsWith('upload')) {
                    this.spinnerService.hide();
                }
                console.log('process response', event);
            }
        }, (err: any) => {
            if (err instanceof HttpErrorResponse) {
                this.spinnerService.hide();
                console.error('process error response', err);
            }
        });
    }

}
