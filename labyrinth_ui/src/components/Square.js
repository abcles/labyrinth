import React from 'react';
import styled from 'styled-components';
import ReactTooltip from "react-tooltip";

const DivSquare = styled.div`
  width: 3rem;
  height: 3rem;
  position:relative;
  display: inline;
  ${props => props.trueval ? 'background: yellow' : ''};
  ${props => props.path ? 'background: orange' : ''};
  ${props => props.trueval && (props.index-props.from === 0) ? 'background: #F2C438' : ''};
  ${props => props.trueval && (props.index-props.to === 0)? 'background: #F2C438' : ''};
 `;


function Square({ index = 0, trueval = false, path = false, from = 0, to = 0, maxindex = 0}) {
    //console.log("props " + index + ' ' + path + ' ' + from + ' ' + to + '  comparison: ' + ((index-from)===0));
    return (
        <DivSquare trueval={trueval} path={path} from={from} to={to} index={index} maxindex={maxindex} data-tip data-for={`indexTip_${index}`} > 
            { (maxindex.toString().length > 2) ?
                <ReactTooltip effect="solid" type="info" id={`indexTip_${index}`} >
                    &nbsp;{index}&nbsp;
                </ReactTooltip>
            : 
                <span>{index}</span>
            }
        </DivSquare>
    );
}

export default Square;