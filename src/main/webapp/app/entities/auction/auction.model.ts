
export const enum AuctionStatus {
    'live',
    'upcoming',
    'closed'

};
export class Auction {
    constructor(
        public id?: number,
        public status?: AuctionStatus,
        public item_name?: string,
        public item_rrp?: number,
        public item_picture?: any,
        public item_overview?: string,
        public max_price?: number,
        public timer?: number,
        public starting_at?: any,
    ) {
    }
}
