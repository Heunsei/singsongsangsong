import { createSlice } from "@reduxjs/toolkit";

const userSlice = createSlice({
  name: "user",
  initialState: {
    idLogin: false,
    useName : "",
  },
  reducers: {},
});

export const userAction = userSlice.actions;
export default userSlice;