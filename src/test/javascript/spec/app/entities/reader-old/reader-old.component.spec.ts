/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { ReaderOldComponent } from '../../../../../../main/webapp/app/entities/reader-old/reader-old.component';
import { ReaderOldService } from '../../../../../../main/webapp/app/entities/reader-old/reader-old.service';
import { ReaderOld } from '../../../../../../main/webapp/app/entities/reader-old/reader-old.model';

describe('Component Tests', () => {

    describe('ReaderOld Management Component', () => {
        let comp: ReaderOldComponent;
        let fixture: ComponentFixture<ReaderOldComponent>;
        let service: ReaderOldService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [ReaderOldComponent],
                providers: [
                    ReaderOldService
                ]
            })
            .overrideTemplate(ReaderOldComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReaderOldComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReaderOldService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ReaderOld(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.readerOlds[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
