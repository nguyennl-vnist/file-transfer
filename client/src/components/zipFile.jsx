
import React from 'react';
import { DropzoneArea } from 'material-ui-dropzone';
import Button from '@mui/material/Button';
import LoadingButton from '@mui/lab/LoadingButton';
import axios from 'axios';
import { AttachFile, Audiotrack, Description, PictureAsPdf, Theaters } from '@material-ui/icons';
import Box from '@mui/material/Box';

function ZipFile(props) {
    const [state, setState] = React.useState({ file: [], res: [] });
    const [loading, setLoading] = React.useState(false);

    const { file, res } = state
    function handleConvert() {
        setLoading(true);
        axios.get("https://reqres.in/api/users?page=2")
            .then((res) => {
                console.log("res", res)
                setLoading(false)
                setState({
                    ...state,
                    res: res.data.data
                })
            })
            .catch(err => console.log(err))
    }
    const handleAddFile = (file) => {
        setState({
            ...state,
            file: file,
            res: []
        })
    }
    const handlePreviewIcon = (fileObject, classes) => {
        const { type } = fileObject.file
        const iconProps = {
            className: classes.image,
        }

        if (type.startsWith("video/")) return <Theaters {...iconProps} />
        if (type.startsWith("audio/")) return <Audiotrack {...iconProps} />

        switch (type) {
            case "application/msword":
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                return <Description {...iconProps} />
            case "application/pdf":
                return <PictureAsPdf {...iconProps} />
            default:
                return <AttachFile {...iconProps} />
        }
    }

    console.log("state", state)
    return (
        <React.Fragment>
            <DropzoneArea
                onChange={handleAddFile}
                filesLimit={100}
                showFileNames={true}
                maxFileSize={30000000}
                showPreviewsInDropzone={true}
                getPreviewIcon={handlePreviewIcon}
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
                    <Button variant="outlined">
                        Download
                    </Button>
                    : " "
                }
            </Box>

        </React.Fragment>


    );
}

export default ZipFile;