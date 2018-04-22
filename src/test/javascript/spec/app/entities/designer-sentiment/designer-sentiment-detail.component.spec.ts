/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerSentimentDetailComponent } from '../../../../../../main/webapp/app/entities/designer-sentiment/designer-sentiment-detail.component';
import { DesignerSentimentService } from '../../../../../../main/webapp/app/entities/designer-sentiment/designer-sentiment.service';
import { DesignerSentiment } from '../../../../../../main/webapp/app/entities/designer-sentiment/designer-sentiment.model';

describe('Component Tests', () => {

    describe('DesignerSentiment Management Detail Component', () => {
        let comp: DesignerSentimentDetailComponent;
        let fixture: ComponentFixture<DesignerSentimentDetailComponent>;
        let service: DesignerSentimentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerSentimentDetailComponent],
                providers: [
                    DesignerSentimentService
                ]
            })
            .overrideTemplate(DesignerSentimentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerSentimentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerSentimentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DesignerSentiment(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.designerSentiment).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
