import React from 'react';
import { DropzoneArea } from 'material-ui-dropzone';
import { AttachFile, Description, PictureAsPdf } from '@material-ui/icons';
import Button from '@mui/material/Button';
import LoadingButton from '@mui/lab/LoadingButton';
import axios from 'axios';
import Box from '@mui/material/Box';

function PdfToTxt(props) {
    const [state, setState] = React.useState({ file: [], res: [] });
    const [loading, setLoading] = React.useState(false);

    const { file, res } = state
    function handleConvert() {
        setLoading(true);
        var bodyFormData = new FormData();
        bodyFormData.append('file', file[0]);
        axios({
            method: "post",
            url: "http://localhost:8080/api/pdf-converter/toTxt",
            data: bodyFormData,
            headers: { "Content-Type": "multipart/form-data" },
        })
            .then(function (response) {
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
        downloadBase64File("application/txt", res, file[0].name.replace(/\.[^/.]+$/, ""));
    }
    const handlePreviewIcon = (fileObject, classes) => {
        const { type } = fileObject.file
        const iconProps = {
            className: classes.image,
        }
        return <Description {...iconProps} />
    }

    console.log("state", state)
    return (
        <React.Fragment>
            PDF TO TXT
            <DropzoneArea
                acceptedFiles={[".pdf"]}
                onChange={handleAddFile}
                filesLimit={1}
                showFileNames={true}
                getPreviewIcon={handlePreviewIcon}
                maxFileSize={30000000}
                showPreviewsInDropzone={true}
            />
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
                    <Button variant="outlined" onClick={handleDownload}>
                        Download
                    </Button>
                    : " "
                }
            </Box>
        </React.Fragment>


    );
}

export default PdfToTxt;