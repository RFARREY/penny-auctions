import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { NinjabidTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CreditDetailComponent } from '../../../../../../main/webapp/app/entities/credit/credit-detail.component';
import { CreditService } from '../../../../../../main/webapp/app/entities/credit/credit.service';
import { Credit } from '../../../../../../main/webapp/app/entities/credit/credit.model';

describe('Component Tests', () => {

    describe('Credit Management Detail Component', () => {
        let comp: CreditDetailComponent;
        let fixture: ComponentFixture<CreditDetailComponent>;
        let service: CreditService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NinjabidTestModule],
                declarations: [CreditDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CreditService,
                    EventManager
                ]
            }).overrideComponent(CreditDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CreditDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Credit(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.credit).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
