import React, { useState } from "react";
import { useForm, Controller } from "react-hook-form";
import Slider from "react-input-slider";
import styled from "styled-components";
import Error from "./components/Error";
import Board from "./components/Board";
import Limits from "./components/Limits";

const DivApp = styled.div`
  text-align: center;
`;
const FormStyles = styled.div`
 padding: 20px;

 form {
   background: white;
   border: 1px solid #dedede;
   display: flex;
   flex-direction: column;
   justify-content: space-around;
   margin: 0 auto;
   max-width: 500px;
   padding: 30px 50px;
 }

 label {
    color: #3d3d3d;
    display: block;
    font-family: sans-serif;
    font-size: 14px;
    font-weight: 500;
    margin-bottom: 5px;
    text-align: left;
}

 input {
    border: 1px solid #d9d9d9;
    border-radius: 4px;
    box-sizing: border-box;
    padding: 10px;
    margin-bottom: 10px;
    width: 100%;
 }
 
 .slider {
    padding: 20px;
    text-align: left;
 }

 .submitButton {
    margin-top: 10px;
	text-align: center;
	padding: 10px;
	text-decoration: none;
    font-size: 16px;
    color: #212529;
    background-color: #7cc;
    box-sizing: border-box;
    border: 1px solid #52bebe;
	border-radius: 4px;
	&:hover {
        color: #212529;
        background-color: #52bebe;
        border-color: #7cc;
        cursor: pointer;
    }
  }
`;

const SubmitButton = styled.button`
    max-width: 500px;
    width: 100%;
    margin-top: 10px;
    text-align: center;
    padding: 10px;
    text-decoration: none;
    font-size: 16px;
    color: #212529;
    background-color: #7cc;
    box-sizing: border-box;
    border: 1px solid #52bebe;
    border-radius: 4px;
    &:hover {
        color: #212529;
        background-color: #52bebe;
        border-color: #7cc;
        cursor: pointer;
    }
}
`;

function getRandomInt(max) {
    return Math.floor(Math.random() * max);
}

function getMatrixNode(arraynode, rows, columns) {
    const row =  Math.floor(arraynode/columns);
    const column =  arraynode%columns;
    const node = {
        'x' : row,
        'y' : column
    }
    return node;
}

function getArrayNode(i, j, columns) {
    return (i*columns) + j;
}

function getMatrix(truenodes, rows, columns) {
    var matrix = [];
    for (var i=0; i<rows; i++) {
        var rowarr = [];
        for (var j=0; j<columns; j++) {
            const index = (i*columns) + j;
            if (truenodes.includes(index)) {
                rowarr.push(true);
            }
            else {
                rowarr.push(false);
            }
        }
        matrix.push(rowarr);
    }
    return matrix;
}

function App() {
  const [rows, setRows] = useState(0);
  const [columns, setColumns] = useState(0);
  const [paths, setPaths] = useState(0);
  const [from, setFrom] = useState(0);
  const [to, setTo] = useState(0);
  const [truenodes, setTrueNodes] = useState([]);
  const [pathnodes, setPathNodes] = useState([]);
  const [error, setError] = useState("");

  const { register, handleSubmit, control, formState: {errors} } = useForm({
      defaultValues: {
          rows: 3,
          columns: 3,
          paths: 50
      }
  });
  const onSubmit = (data) => {
      setRows(data?.rows);
      setColumns(data?.columns);
      setPaths(data?.paths);
      setError("");

      
      // Get the number of nodes that should be set to true
      const countpath = Math.ceil(((data?.rows*data?.columns)*data?.paths)/100);
      //console.log("Generate randomly as path this number of true values: "+ countpath);
      // Keep the nodes indexes that would be randomly set to true;
      let truenodes = [];
      while (truenodes.length < countpath) {
          const node = getRandomInt(data?.rows*data?.columns);
          if (!truenodes.includes(node)) {
              truenodes.push(node);
          }
      }
      //console.log("These nodes would be set as true: " + truenodes);
      setTrueNodes(truenodes);
      setPathNodes([]);

      setFrom(0);
      var lastNode = 0;
      if (rows*columns > 0) { lastNode = data?.rows*data?.columns-1; } 
      setTo(lastNode);
  }

  const clickEventFindPath = async () => {
      //console.log("From/to: " + from + ' ' + to);
      setError("");

      const url = "http://localhost:8080/api/getpath"
      const data = {
          "from": getMatrixNode(from, rows, columns),
          "to": getMatrixNode(to, rows, columns),
          "matrix": getMatrix(truenodes, rows, columns)
      }
      await fetch(url, {
          method: 'POST', 
          cache: 'no-cache',
          credentials: 'omit',
          headers: {
              'Content-Type': 'application/json'
          },
          referrerPolicy: 'no-referrer',
          body: JSON.stringify(data)
      }).then( response => {
          if (response?.status !== 200) {
              return Promise.reject(response);
          } else {
              return response.json();
          }
      }).then( data => {
          var pn = [];
          data.map((x, i) => {
              pn.push(getArrayNode(x?.row, x?.column, columns));
          })
          setPathNodes(pn);
      }).catch(error => {
          setPathNodes([]);
          if (error?.status === 404) {
              setError("Path not found (" + error?.status + (error?.message ? " - " + error?.message : "") + ")");
          } else {
              setError("System error (" + error?.status + (error?.message ? " - " + error?.message : "") + ")");
          }
          
      });

  }
  return (
    <DivApp>
      <h1>Labyrinth app</h1>
      <FormStyles>
            <form onSubmit={ handleSubmit(onSubmit) }>
                <label>Numbers of rows:</label>
                <input name="rows"  
                    {...register("rows", {
                        required: {
                            value: true,
                            message: "Rows is required"
                        }, 
                        max: {
                            value: 30,
                            message: "Rows should be less than 30"
                        },
                        validate: {
                            positive: v => parseInt(v) > 0 || "Rows should be greather than 0",
                        },
                        valueAsNumber: true
                    })} 
                    placeholder="Submit the labyrinth number of rows" />
                <Error errors={errors?.rows} />
                <label>Numbers of columns:</label>
                <input name="columns" 
                    {...register("columns", { 
                        required: {
                            value: true,
                            message: "Columns is required"
                        }, 
                        max: {
                            value: 30,
                            message: "Columns should be less than 30"
                        },
                        validate: {
                            positive: v => parseInt(v) > 0 || "Columns should be greather than 0",
                        },
                        valueAsNumber: true
                    })} 
                    placeholder="Submit the labyrinth number of columns" />
                <Error errors={errors?.columns} />
                <label>Percent of path:</label>
                <div className = "slider">
                    <Controller
                        control={control}
                        name="paths"
                        defaultValue={50}
                        render={({ field: { value, onChange } }) => (
                            <Slider
                                axis={"x"}
                                xmax={100}
                                xmin={1}
                                xstep={1}
                                onChange={({ x }) => onChange(x)}
                                x={value}
                            />
                        )}
                    />
                </div>
                <input type="submit" className="submitButton" value="Generate the labyrinth" />
            </form>
            <Board rows = {rows} columns = {columns} paths = {paths} truenodes = {truenodes} pathnodes={pathnodes} from = {from} to = {to} error = {error}/>
            
            {(truenodes.length > 0) && 
                <>
                    <Limits 
                        truenodes = {truenodes} 
                        lastnode = { (rows*columns > 0) ? rows*columns-1 : 0 } 
                        from = {from}
                        setFrom = {setFrom}
                        to = {to}
                        setTo = {setTo} />
                    <SubmitButton 
                        onClick = {clickEventFindPath} >Validate if any path exists between these nodes</SubmitButton>
                </>
            }
        </FormStyles>
    </DivApp>
  );
}

export default App;
