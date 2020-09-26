import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { AuthJwtServerProvider } from "../services/auth-jwt.service";
import { WebsocketService } from "../services/websocket.service";
import { HttpClient } from "@angular/common/http";
import { AclService } from "../services/acl.service";
import { Auction } from "../model/auction.model";

@Component({
  selector: 'app-root',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class LandingComponent implements OnInit {
  webSocketService: WebsocketService;
  topic: string = "/topic/auctions";
  auctions: Auction[];
  displayedColumns: string[] = ['type', 'ownername', 'itemid', 'targetcountries', 'tags', 'startingBid', 'startDate', 'endDate'];
  userName = '';
  clickMessage = '';

  constructor(private http: HttpClient, private authProvider: AuthJwtServerProvider, private aclService: AclService) {
    this.webSocketService = new WebsocketService(this.topic, x => this.handleMessage(x));
    this.webSocketService._connect();
    this.userName = authProvider.getIdentity().sub;
  }

  ngOnInit(): void {
  }

  handleMessage(auctionMessage: string) {
    console.log("------- Incoming auctionmessage: " + auctionMessage);
    this.auctions = JSON.parse(auctionMessage);
  }

  isVerified(): boolean {
    return this.aclService.hasRole('create');
  }

  onCreate(type: HTMLInputElement, itemId: HTMLInputElement, description: HTMLInputElement,
           targetCountries: HTMLInputElement, tags: HTMLInputElement, startingBid: HTMLInputElement,
           startDate: HTMLInputElement, endDate: HTMLInputElement) {
    this.http.post<AuctionCreateRequest>("/auction", new AuctionCreateRequest(type.value, this.userName, itemId.value,
      description.value, targetCountries.value, tags.value, startingBid.value, startDate.value, endDate.value))
      .subscribe(
        response  => {
          type.value = '';
          itemId.value = '';
          description.value = '';
          targetCountries.value = '';
          tags.value = '';
          startingBid.value = '';
          startDate.value = '';
          endDate.value = '';
        },
        error => this.clickMessage = error.json
      );
  }
}

export class AuctionCreateRequest {
  type;
  owner;
  itemId;
  description;
  targetCountries;
  tags;
  startingBid;
  startDate;
  endDate;

  constructor(type: string, owner: string, itemId: string, description: string, targetCountries: string,
              tags: string, startingBid: string, startDate: string, endDate: string) {
    this.type = type
    this.owner = owner
    this.itemId = itemId
    this.description = description
    this.targetCountries = targetCountries
    this.tags = tags
    this.startingBid = startingBid
    this.startDate = startDate
    this.endDate = endDate
  }
}
