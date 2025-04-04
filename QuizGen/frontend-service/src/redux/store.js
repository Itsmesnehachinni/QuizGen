import { createStore } from 'redux';

const initialState = {
    role : null,
    customerId: null,
    pharmacyId : null,
    isLoggedIn : null
};

const userReducer = (state = initialState, action) => {
    switch (action.type) {
        case 'SET_ROLE_LOGIN':
            return { ...state, role: action.payload };
        case 'SET_LOGOUT':
            return { ...state, 
                role: null,
                customerId: null,
                pharmacyId: null,
                isLoggedIn: null,
             };
        case 'SET_CUSTOMER_ID':
                return { ...state, customerId: action.payload };
        case 'SET_PHARMACY_ID':
                    return { ...state, pharmacyId: action.payload };
        case 'SET_IS_LOGGED_IN':
                    return{...state, isLoggedIn: action.payload};       
        default:
            return state;
    }
};

export const store = createStore(userReducer);