import { Component, OnInit, ViewEncapsulation } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ActivatedRoute } from "@angular/router";
import { WebsocketService } from "../services/websocket.service";
import {Auction} from "../model/auction.model";
import {AuctionCreateRequest} from "./landing.component";

@Component({
  selector: 'auction-component',
  templateUrl: './auction.component.html',
  styleUrls: ['./auction.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class AuctionComponent implements OnInit {
  webSocketService: WebsocketService;
  auctionRoot = '/topic/auction/';
  displayedColumns: string[] = ['bidder', 'bid', 'timestamp'];
  auctionId = '';
  bids: Bid[];
  auction: Auction;
  errorMessage: '';

  constructor(private http: HttpClient, private route: ActivatedRoute) {
    this.route.params.subscribe(params => this.auctionId = params['auctionId']);
    this.webSocketService = new WebsocketService(this.auctionRoot + this.auctionId, x => this.handleMessage(x));
    this.webSocketService._connect();
  }

  ngOnInit(): void {
    this.getAuction();
  }

  getAuction() {
    return this.http.get<Auction>("/auction/" + this.auctionId).subscribe(
      response => this.auction = response,
      error => this.errorMessage = error.json
    )
  }

  handleMessage(bidMessage: string) {
    console.log("------- Incoming bidmessage: " + bidMessage);
    this.bids = JSON.parse(bidMessage);
  }

  placeBid(bid: HTMLInputElement) {
    this.http.post<BidRequest>("/auction/" + this.auctionId + "/bid", new BidRequest(bid.value))
      .subscribe(
        response  => {
          bid.value = '';
        },
        error => this.errorMessage = error.json
      );
  }
}

export interface Bid {
  id: string;
  bidder: string;
  auctionId: string;
  bid: string;
  originalCurrency: string;
  exchangeRate: string;
  timestamp: string;
}

export class BidRequest {
  bid = '';

  constructor(bid: string) {
    this.bid = bid
  }
}
