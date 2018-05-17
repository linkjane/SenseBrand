/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerAwardComponent } from '../../../../../../main/webapp/app/entities/designer-award/designer-award.component';
import { DesignerAwardService } from '../../../../../../main/webapp/app/entities/designer-award/designer-award.service';
import { DesignerAward } from '../../../../../../main/webapp/app/entities/designer-award/designer-award.model';

describe('Component Tests', () => {

    describe('DesignerAward Management Component', () => {
        let comp: DesignerAwardComponent;
        let fixture: ComponentFixture<DesignerAwardComponent>;
        let service: DesignerAwardService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerAwardComponent],
                providers: [
                    DesignerAwardService
                ]
            })
            .overrideTemplate(DesignerAwardComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerAwardComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerAwardService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DesignerAward(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.designerAwards[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
