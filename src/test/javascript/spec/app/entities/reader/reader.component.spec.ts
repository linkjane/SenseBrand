/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { ReaderComponent } from '../../../../../../main/webapp/app/entities/reader/reader.component';
import { ReaderService } from '../../../../../../main/webapp/app/entities/reader/reader.service';
import { Reader } from '../../../../../../main/webapp/app/entities/reader/reader.model';

describe('Component Tests', () => {

    describe('Reader Management Component', () => {
        let comp: ReaderComponent;
        let fixture: ComponentFixture<ReaderComponent>;
        let service: ReaderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [ReaderComponent],
                providers: [
                    ReaderService
                ]
            })
            .overrideTemplate(ReaderComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReaderComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReaderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Reader(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.readers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
