import React from 'react';
import styled from 'styled-components';
import Square from './Square';

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
const BoardDiv = styled.div`
  border:2px solid gray;
  font: 14px Arial;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;

  > div {
    flex: 1 1 auto;
    width: ${props => 100/props.columns}%;
    box-sizing:border-box;
    border:1px solid gray;
    text-align:center;
    line-height:20px;
    padding:5px;
  }
}
`;
const Error = styled.div`
  background-color: red;
  color: white;
  margin-top: 10px;
`;

function Board({ rows = 0, columns = 0, paths = 50, truenodes = [], pathnodes=[], from = 0, to = 0, maxindex = 0, error = "" }) {
  //console.log('Board should be visible? ' +  (rows > 0 && columns > 0) + ' (rows = ' + rows + ", columns = " + columns + ", paths = " + paths + ")");

  return (
    (rows > 0 && columns > 0) &&
        <Framework>
          <BoardDiv columns={columns}>
            {[...Array(rows)].map((x, i) =>
              [...Array(columns)].map((y, j) =>
                <Square 
                  key={ i + '' + j } 
                  index={ (i*columns) + j }
                  trueval={ (truenodes.includes((i*columns) + j)) ? true : false }
                  path={ (pathnodes.includes((i*columns) + j)) ? true : false }
                  from = { from }
                  to = { to } 
                  maxindex = { rows*columns-1 }
                />
            ))}
          </BoardDiv>
          {error &&
            <Error>{error}</Error>
          }
        </Framework>
  );
}

export default Board;