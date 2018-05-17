/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenseBrandTestModule } from '../../../test.module';
import { DesignerSentimentComponent } from '../../../../../../main/webapp/app/entities/designer-sentiment/designer-sentiment.component';
import { DesignerSentimentService } from '../../../../../../main/webapp/app/entities/designer-sentiment/designer-sentiment.service';
import { DesignerSentiment } from '../../../../../../main/webapp/app/entities/designer-sentiment/designer-sentiment.model';

describe('Component Tests', () => {

    describe('DesignerSentiment Management Component', () => {
        let comp: DesignerSentimentComponent;
        let fixture: ComponentFixture<DesignerSentimentComponent>;
        let service: DesignerSentimentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenseBrandTestModule],
                declarations: [DesignerSentimentComponent],
                providers: [
                    DesignerSentimentService
                ]
            })
            .overrideTemplate(DesignerSentimentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DesignerSentimentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DesignerSentimentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DesignerSentiment(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.designerSentiments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
