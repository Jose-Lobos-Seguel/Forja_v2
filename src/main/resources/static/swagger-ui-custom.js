window.onload = function() {
  if (window.ui) {
    window.ui.initOAuth && window.ui.initOAuth();
  }
};

window.swaggerUIBundle = window.swaggerUIBundle || {};

window.swaggerOptions = window.swaggerOptions || {};
window.swaggerOptions.operationsSorter = function(a, b) {
  const order = { get: 1, post: 2, put: 3, delete: 4, patch: 5, head: 6, options: 7, trace: 8 };
  const methodA = a.get('method').toLowerCase();
  const methodB = b.get('method').toLowerCase();
  const orderA = order[methodA] || 99;
  const orderB = order[methodB] || 99;
  return orderA - orderB;
};