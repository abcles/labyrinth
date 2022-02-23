import React from "react";
import styled from "styled-components";

const Framework = styled.div`
  padding: 20px;
  background: white;
  border: 1px solid #dedede;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  margin: 0 auto;
  margin-top: 10px;
  max-width: 500px;
  padding: 30px 50px;
`;

const Label = styled.label`
    color: #3d3d3d;
    display: block;
    font-family: sans-serif;
    font-size: 14px;
    font-weight: 500;
    margin-bottom: 5px;
    text-align: left;
`;

const Select = styled.select`
    border: 1px solid #d9d9d9;
    border-radius: 4px;
    box-sizing: border-box;
    padding: 10px;
    margin-bottom: 10px;
    width: 100%;
`;

function Limits({ truenodes = [], lastnode = 0, from, setFrom, to, setTo }) {
    //console.log("Nodes received: " + truenodes);   
    return (
        <Framework>
            <Label>From:</Label>
            <Select 
                value={from}
                onChange={e => { setFrom(e.target.value); }}>
                {[...Array(lastnode+1)].map((x, i) => 
                    <option value={i} key={"from"+ i}>{i}</option>
                )}
            </Select>
            <Label>To:</Label>
            <Select 
                value={to}
                onChange={e => { setTo(e.target.value); }}>
                {[...Array(lastnode+1)].map((x, i) => 
                    <option value={i} key={"to"+ i}>{i}</option>
                )}
            </Select>
        </Framework>
    );
}

export default Limits;