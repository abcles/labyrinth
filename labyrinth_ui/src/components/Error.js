import React from 'react';
import styled from 'styled-components';

const DivError = styled.div`
    color: red;
    font-family: sans-serif;
    font-size: 12px;
    height: 20px;
 `;
 
function Error({ errors }) {
    return (
        <DivError>{errors ? errors.message : " "}</DivError>
    );
}

export default Error;