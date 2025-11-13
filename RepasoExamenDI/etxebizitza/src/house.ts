export interface House {

    "id": string;
    "name": string;
    "location": {
        "city": string;
        "address": string;
        "lat": number;
        "long": number;
    }
    "propertyType": HouseTypes;
    "isForSale": boolean;
    "salePrice": number;
    "isForRent": boolean;
    "rentPrice": number;
    "picture": Base64URLString;
}

export enum HouseTypes{
    APARTMENT = "apartment",
    DETACHED_HOUSE = "detached",
    GARAJEPLOT = "garaje_plot",
    OFFICE = "office"
}
