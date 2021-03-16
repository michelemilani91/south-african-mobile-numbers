export interface Page<T> {
    content: T[];
    pageable: {
        sort: {
            unsorted: boolean;
            sorted: boolean;
            empty: boolean;
        };
        offset: number;
        pageNumber: number;
        pageSize: number;
        paged: boolean;
        unpaged: boolean
    };
    totalPages: number;
    totalElements: number;
    last: boolean;
    number: number;
    numberOfElements: number;
    size: number;
    first: boolean;
    sort: {
        unsorted: boolean;
        sorted: boolean;
        empty: boolean;
    };
    empty: boolean;
}

export enum ElaborationStatus {
    UNPROCESSED = "UNPROCESSED",
    PROCESSING = "PROCESSING",
    PROCESSED = "PROCESSED"
}

export interface Elaboration {
    id: number;
    name: string;
    status: ElaborationStatus;
}

export interface MobileNumber {
  id: number;
  originalId: string;
  originalMobileNumber: string;
  correctedMobileNumber?: string;
  valid: boolean;
}
