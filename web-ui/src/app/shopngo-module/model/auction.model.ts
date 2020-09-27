export interface Auction {
  id: string;
  type: string;
  ownerName: string;
  itemId: string;
  itemName: string;
  description: string;
  targetCountries: string;
  tags: string;
  startingBid: string;
  startDate: string;
  endDate: string;
}
