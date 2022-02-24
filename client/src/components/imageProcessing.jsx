import React from 'react';
import { DropzoneArea } from 'material-ui-dropzone';
import { AttachFile, Description, PictureAsPdf } from '@material-ui/icons';
import Button from '@mui/material/Button';
import LoadingButton from '@mui/lab/LoadingButton';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import axios from 'axios';
import Box from '@mui/material/Box';

function ImageProcessing(props) {
    const [state, setState] = React.useState({ file: [], res: [] });
    const [loading, setLoading] = React.useState(false);
    const [type, setType] = React.useState('mirror-image');

    const handleChange = (event) => {
        setType(event.target.value);
    };
    const { file, res } = state
    function handleConvert() {
        setLoading(true);
        var bodyFormData = new FormData();
        bodyFormData.append('file', file[0]);
        console.log(bodyFormData)
        axios({
            method: "post",
            url: "http://localhost:8080/api/image-processing/" + type,
            data: bodyFormData,
            headers: { "Content-Type": "multipart/form-data" },
        })
            .then(function (response) {
                console.log("response.data", typeof response.data.file, response.data.file)
                setLoading(false)
                setState({
                    ...state,
                    res: response.data.file
                })
            })
            .catch(function (response) {
                //handle error
                console.log(response);
            });
    }

    function downloadBase64File(contentType, base64Data, fileName) {
        const linkSource = `data:${contentType};base64,${base64Data}`;
        const downloadLink = document.createElement("a");
        downloadLink.href = linkSource;
        downloadLink.download = fileName;
        downloadLink.click();
    }
    const handleAddFile = (file) => {
        setState({
            ...state,
            file: file,
            res: []
        })
    }
    const handleDownload = () => {
        console.log("res", res)
        downloadBase64File("image/jpg", res, file[0].name.replace(/\.[^/.]+$/, ""));
    }

    console.log("state", state)
    console.log("imgUrl", "data:image/jpeg;charset=utf-8;base64," + res)
    return (
        <React.Fragment>
            IMAGE PROCESSING
            <DropzoneArea
                acceptedFiles={['image/*']}
                onChange={handleAddFile}
                filesLimit={1}
                showFileNames={true}
                maxFileSize={30000000}
                showPreviewsInDropzone={true}
            />
            <Box p={1}>
                <FormControl >
                    <InputLabel id="demo-simple-select-label">Filter</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        value={type}
                        label="Type"
                        onChange={handleChange}
                    >
                        <MenuItem value={'mirror-image'}>Mirror Image</MenuItem>
                        <MenuItem value={'negative-color'}>Invert Color</MenuItem>
                        <MenuItem value={'write-text'}>Add Watermark</MenuItem>
                    </Select>
                </FormControl>
            </Box>

            <Box p={1}>
                {file?.length && !res?.length ?
                    <LoadingButton
                        onClick={handleConvert}
                        loading={loading}
                        variant="outlined"
                    >
                        Convert
                    </LoadingButton>
                    :
                    <Button variant="outlined" disabled>
                        Convert
                    </Button>}
                {(res?.length) ?
                    <React.Fragment>
                        <Button variant="outlined" onClick={() => handleDownload()}>
                            Download
                        </Button>
                        <div style={{ paddingTop: "10px" }}>
                            <img src={"data:image/jpeg;charset=utf-8;base64," + res} alt="" style={{ width: "100%" }} />
                        </div>
                    </React.Fragment>

                    : " "
                }
            </Box>

        </React.Fragment>


    );
}

export default ImageProcessing;