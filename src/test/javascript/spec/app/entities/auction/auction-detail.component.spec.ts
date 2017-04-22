import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { NinjabidTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AuctionDetailComponent } from '../../../../../../main/webapp/app/entities/auction/auction-detail.component';
import { AuctionService } from '../../../../../../main/webapp/app/entities/auction/auction.service';
import { Auction } from '../../../../../../main/webapp/app/entities/auction/auction.model';

describe('Component Tests', () => {

    describe('Auction Management Detail Component', () => {
        let comp: AuctionDetailComponent;
        let fixture: ComponentFixture<AuctionDetailComponent>;
        let service: AuctionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [NinjabidTestModule],
                declarations: [AuctionDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AuctionService,
                    EventManager
                ]
            }).overrideComponent(AuctionDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AuctionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AuctionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Auction(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.auction).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
