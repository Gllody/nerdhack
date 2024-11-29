const fs = require("fs");
const path = require("path");

const branch = process.argv[2];
const compareUrl = process.argv[3];
const success = process.argv[4] === "true";
