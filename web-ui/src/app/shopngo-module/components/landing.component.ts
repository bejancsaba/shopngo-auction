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
  items: Item[];
  selectedItem: string;
  auctionTypes: String[];
  selectedType: string;
  countries: String[];
  selectedCountry: string;
  displayedColumns: string[] = ['type', 'ownername', 'itemname', 'targetcountries', 'tags', 'startingBid', 'startDate', 'endDate'];
  userName = '';
  clickMessage = '';
  startDate: Date;

  constructor(private http: HttpClient, private authProvider: AuthJwtServerProvider, private aclService: AclService) {
    this.webSocketService = new WebsocketService(this.topic, x => this.handleMessage(x));
    this.webSocketService._connect();
    this.userName = authProvider.getIdentity().sub;
    this.startDate = new Date();
  }

  ngOnInit(): void {
    this.getItems();
    this.getAuctionTypes();
    this.getCountries();
  }

  handleMessage(auctionMessage: string) {
    this.auctions = JSON.parse(auctionMessage);
  }

  isVerified(): boolean {
    return this.aclService.hasRole('create');
  }

  onCreate(description: HTMLInputElement, tags: HTMLInputElement, startingBid: HTMLInputElement,
           startDate: HTMLInputElement, endDate: HTMLInputElement) {
    console.log("---- Start Date: " + this.startDate.getMilliseconds())
    this.http.post<AuctionCreateRequest>("/auction", new AuctionCreateRequest(this.selectedType, this.userName, this.selectedItem,
      description.value, this.selectedCountry, tags.value, startingBid.value, startDate.value, endDate.value))
      .subscribe(
        response  => {
          this.selectedType = '';
          this.selectedItem = '';
          description.value = '';
          this.selectedCountry = '';
          tags.value = '';
          startingBid.value = '';
          startDate.value = '';
          endDate.value = '';
        },
        error => this.clickMessage = error.json
      );
  }

  getItems() {
    return this.http.get<Item[]>("/items").subscribe(
      response => this.items = response,
      error => console.log(error.json)
    )
  }

  getAuctionTypes() {
    return this.http.get<String[]>("/static/auctionTypes").subscribe(
      response => this.auctionTypes = response,
      error => console.log(error.json)
    )
  }

  getCountries() {
    return this.http.get<String[]>("/static/countries").subscribe(
      response => this.countries = response,
      error => console.log(error.json)
    )
  }
}

export interface Item {
  id: String;
  name: String;
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
